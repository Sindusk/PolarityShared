package network;

import netdata.updates.MatrixUpdate;
import events.ProjectileEvent;
import com.jme3.network.ConnectionListener;
import com.jme3.network.Filters;
import com.jme3.network.HostedConnection;
import com.jme3.network.Message;
import com.jme3.network.MessageListener;
import com.jme3.network.Network;
import com.jme3.network.Server;
import com.jme3.network.serializing.Serializer;
import java.io.IOException;
import java.util.concurrent.Callable;
import main.GameServer;
import netdata.*;
import character.CharacterManager;
import character.Player;
import com.jme3.scene.Node;
import events.EventChain;
import files.properties.PlayerProperties;
import files.properties.ServerProperties;
import files.properties.vars.PlayerVar;
import items.Inventory;
import items.creation.ItemGenerator;
import spellforge.SpellMatrix;
import tools.DevCheats;
import tools.Util;

/**
 * 
 * @author Sindusk
 */
public class ServerNetwork extends GameNetwork{
    // Constants:
    private static final String SERVER_PROPERTIES_FILENAME  = "properties/server/server.properties";
    private static final String PLAYER_PROPERTIES_PATH      = "properties/player/";
    private static final String PLAYER_DATA_PATH            = "data/player/";
    
    // Important variables:
    private ServerListener listener = new ServerListener();
    protected final GameServer app;
    protected CharacterManager charManager;
    protected Server server;
    protected ServerProperties settings = new ServerProperties(SERVER_PROPERTIES_FILENAME);
    protected ServerStatus status = new ServerStatus();
    
    public ServerNetwork(GameServer app){
        this.app = app;
        this.charManager = app.getCharManager();
        try {
            server = Network.createServer(6143);
            registerSerials();
            settings.load();
            status.loadSettings(settings);
            server.addConnectionListener(listener);
            server.start();
        }catch (IOException ex){
            Util.log(ex);
        }
    }
    
    private void registerClass(Class c){
        Serializer.registerClass(c);
        server.addMessageListener(listener, c);
    }
    private void registerSerials(){
        for(NetData d : NetData.values()){
            registerClass(d.c);
        }
    }
    
    public void stop(){
        server.close();
    }
    
    public void send(Message m){
        if(server != null){
            server.broadcast(m);
        }
    }
    
    private class ServerListener implements MessageListener<HostedConnection>, ConnectionListener{
        public void connectionAdded(Server server, HostedConnection conn) {
            // Nothing needed here.
        }
        public void connectionRemoved(Server server, HostedConnection conn) {
            int id = charManager.remove(conn);
            if(id == -1){
                return;
            }
            Player p = charManager.getPlayer(id);
            PlayerProperties properties = new PlayerProperties(PLAYER_PROPERTIES_PATH+p.getName()+".properties");
            properties.savePlayerData(p);
            server.broadcast(new DisconnectData(id));
            conn.close("Disconnected");
            Util.log("[Connection Removed] Player "+id+" ("+p.getName()+") has disconnected.");
        }
        
        // HANDSHAKING PROCESS BEGINS
        
        // Starts with version check from ConnectData
        /** Recieved when a player first pings the server, checking if a slot is open.
         * <p>
         * This method determines if the player has the correct version & if there's an open
         * slot in the server before telling the client it's a successful connection.
         * If the checks pass, this will send an ID back to the player for connection.
         * @param d Data from the message.
         */
        private void ConnectMessage(final HostedConnection source, final ConnectData d){
            Util.log("[ServerNetwork] <ConnectMessage> Recieving new connection...", 1);
            if(d.getVersion().equals(app.getVersion())){    // Ensures application versions match
                app.enqueue(new Callable<Void>(){
                    public Void call() throws Exception{
                        int id = charManager.findEmptyID();    // Find an empty slot for the player, if one exists
                        if(id != -1){
                            //connection.send(new ServerStatusData(status));
                            if(settings.getVar("serverPlayerData").equals("true")){
                                PlayerProperties properties = new PlayerProperties(PLAYER_PROPERTIES_PATH+d.getName()+".properties");
                                properties.load();
                                if(properties.getVar(PlayerVar.PlayerName.getVar()).equals("")){
                                    properties.setVar(PlayerVar.PlayerName.getVar(), d.getName());
                                    properties.save();
                                }
                                PlayerData pd = properties.getPlayerData(id);
                                Inventory inv = new Inventory();
                                int i = 0;
                                while(i < 15){
                                    inv.add(ItemGenerator.randomItem(inv, (int)Util.scaledRandFloat(1, 50)));
                                    i++;
                                }
                                pd.setInventory(inv);
                                source.send(new PlayerConnectionData(id, pd));
                            }else{
                                source.send(new PlayerIDData(id));
                            }
                        }else{
                            source.close("Server is full.");
                        }
                        return null;
                    }
                });
            }else{  // Decline connection if the client version does not match
                Util.log("[Connect Message] ERROR: Client has incorrect version. A player was denied connection.");
                source.close("Invalid Version. [Client: "+d.getVersion()+"] [Server: "+app.getVersion()+"]");
            }
        }
        
        // SSPD off - The player sends back his player data for the server to add, then send to all other players
        /**
         * Message is recieved when a player is authenticated for joining the server.
         * <p>
         * This contains all the data for the player themselves, such as character stats, location, etc.
         * @param d The data of the message.
         */
        private void PlayerMessage(final HostedConnection source, final PlayerData d){
            Util.log("[ServerNetwork] <PlayerMessage> Player "+d.getID()+" (v"+app.getVersion()+") connected successfully.");
            final Player player = charManager.add(d);
            player.setConnection(source);
            app.enqueue(new Callable<Void>(){
                public Void call() throws Exception{
                    player.initializeMatrixArray(new Node());
                    DevCheats.initPlayerMatrix(source, d.getID(), player.getMatrix(0));
                    return null;
                }
            });
            server.broadcast(Filters.notEqualTo(source), d);
            app.getWorld().sendData(source);
            charManager.sendData(source);
        }
        
        // HANDSHAKING PROCESS ENDS
        
        // SPELL MATRIX
        
        private void MatrixUpdateMessage(final HostedConnection source, final MatrixUpdate d){
            app.enqueue(new Callable<Void>(){
                public Void call() throws Exception{
                    source.send(d);
                    charManager.updateMatrix(d);
                    return null;
                }
            });
        }
        
        // END SPELL MATRIX
        
        private void ActionMessage(final HostedConnection source, final ActionData d){
            app.enqueue(new Callable<Void>(){
                public Void call() throws Exception{
                    Player owner = charManager.getPlayer(d.getID());
                    SpellMatrix matrix = owner.getMatrix(d.getSlot());
                    EventChain events = new EventChain();
                    events.addEvents(matrix.calculateEvents(source, d));
                    events.execute(server, app);
                    //matrix.calculateEvent(connection, d);
                    //float speed = 15;
                    //Projectile p = app.getWorld().addProjectile(new ProjectileEvent(owner, d.getStart(), d.getTarget(), event, speed));
                    //server.broadcast(new ProjectileData(p.hashCode(), owner.getID(), d.getStart(), d.getTarget(), speed));
                    return null;
                }
            });
        }
        
        private void DamageMessage(HostedConnection source, final DamageData d){
            server.broadcast(Filters.notEqualTo(source), d);
            app.enqueue(new Callable<Void>(){
                public Void call() throws Exception{
                    //characterManager.damagePlayer(d);
                    return null;
                }
            });
        }
        
        /** Recieved when a player moves.
         * <p>
         * This method is mainly used to broadcast the movement
         * @param d Data from the message.
         */
        private void MoveMessage(HostedConnection source, final MoveData d){
            server.broadcast(Filters.notEqualTo(source), d);
            app.enqueue(new Callable<Void>(){
                public Void call() throws Exception{
                    charManager.updatePlayerLocation(d);
                    return null;
                }
            });
        }
        /**
         * Ping data is empty, simply used as a timing device.
         * @param d Data from the message.
         */
        private void PingMessage(HostedConnection source, PingData d){
            source.send(d);
        }
        /**
         * Message recieved when a new Projectile is spawned.
         * <p>
         * Holds all data required to reproduce the projectile.
         * @param d The data of the message.
         */
        private void ProjectileMessage(ProjectileData d){
            final ProjectileData m = d;
            app.enqueue(new Callable<Void>(){
                public Void call() throws Exception{
                    ProjectileEvent attack = new ProjectileEvent(charManager, m);
                    app.getWorld().addProjectile(attack);
                    server.broadcast(m);
                    return null;
                }
            });
        }
        private void SoundMessage(SoundData d){
            server.broadcast(d);
        }
        
        public void messageReceived(HostedConnection source, Message m) {
            //server = source.getServer();
            
            // Handshaking
            if(m instanceof ConnectData){   // Version Checking
                ConnectMessage(source, (ConnectData) m);
            }else if(m instanceof PlayerData){
                PlayerMessage(source, (PlayerData) m);
            }
            // Spell Matrix
            else if(m instanceof MatrixUpdate){
                MatrixUpdateMessage(source, (MatrixUpdate) m);
            }
            // Quick actions
            else if(m instanceof ActionData){
                ActionMessage(source, (ActionData) m);
            }else if(m instanceof DamageData){
                DamageMessage(source, (DamageData) m);
            }else if(m instanceof MoveData){
                MoveMessage(source, (MoveData) m);
            }else if(m instanceof PingData){
                PingMessage(source, (PingData) m);
            }else if (m instanceof ProjectileData){
                ProjectileMessage((ProjectileData) m);
            }else if(m instanceof SoundData){
                SoundMessage((SoundData) m);
            }
        }
    }
}
