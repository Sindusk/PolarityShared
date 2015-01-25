package network;

import action.Event;
import action.ProjectileAttack;
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
import entity.Entity;
import entity.PlayerEntity;
import entity.Projectile;
import files.properties.PlayerProperties;
import files.properties.ServerProperties;
import files.properties.vars.PlayerVar;
import java.util.ArrayList;
import netdata.requests.InventoryRequest;
import netdata.responses.InventoryResponse;
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
    protected Server server;
    protected ServerProperties settings = new ServerProperties(SERVER_PROPERTIES_FILENAME);
    protected ServerStatus status = new ServerStatus();
    
    // Game variables:
    protected CharacterManager characterManager = new CharacterManager();
    
    public ServerNetwork(GameServer app){
        this.app = app;
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
        private HostedConnection connection;
        
        public void connectionAdded(Server server, HostedConnection conn) {
            // Nothing needed here.
        }
        public void connectionRemoved(Server server, HostedConnection conn) {
            int id = characterManager.remove(conn);
            if(id == -1){
                return;
            }
            Player p = characterManager.getPlayer(id);
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
        private void ConnectMessage(final ConnectData d){
            Util.log("[ServerNetwork] <ConnectMessage> Recieving new connection...", 1);
            if(d.getVersion().equals(app.getVersion())){    // Ensures application versions match
                app.enqueue(new Callable<Void>(){
                    public Void call() throws Exception{
                        int id = characterManager.findEmptyID();    // Find an empty slot for the player, if one exists
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
                                connection.send(new PlayerConnectionData(id, pd));
                            }else{
                                connection.send(new PlayerIDData(id));
                            }
                        }else{
                            connection.close("Server is full.");
                        }
                        return null;
                    }
                });
            }else{  // Decline connection if the client version does not match
                Util.log("[Connect Message] ERROR: Client has incorrect version. A player was denied connection.");
                connection.close("Invalid Version. [Client: "+d.getVersion()+"] [Server: "+app.getVersion()+"]");
            }
        }
        
        // SSPD off - The player sends back his player data for the server to add, then send to all other players
        /**
         * Message is recieved when a player is authenticated for joining the server.
         * <p>
         * This contains all the data for the player themselves, such as character stats, location, etc.
         * @param d The data of the message.
         */
        private void PlayerMessage(PlayerData d){
            Util.log("[ServerNetwork] <PlayerMessage> Player "+d.getID()+" (v"+app.getVersion()+") connected successfully.");
            characterManager.add(d);
            server.broadcast(Filters.notEqualTo(connection), d);
            characterManager.getPlayer(d.getID()).setConnection(connection);
            app.getWorld().sendData(connection);
            characterManager.sendData(connection);
        }
        
        // HANDSHAKING PROCESS ENDS
        
        private void ActionMessage(final ActionData d){
            app.enqueue(new Callable<Void>(){
                public Void call() throws Exception{
                    Player owner = characterManager.getPlayer(d.getID());
                    Event event = new Event(){
                        @Override
                        public boolean onCollide(ArrayList<Entity> collisions){
                            int i = 0;
                            Entity t;   // Temp entity
                            while(i < collisions.size()){
                                t = collisions.get(i);
                                if(t instanceof PlayerEntity){
                                    PlayerEntity pe = (PlayerEntity) t; // Cast the Entity to a PlayerEntity to open up specific methods
                                    pe.damage(10);
                                    return true;
                                }
                                i++;
                            }
                            return false;
                        }
                    };
                    float speed = 15;
                    Projectile p = app.getWorld().addProjectile(new ProjectileAttack(owner, d.getStart(), d.getTarget(), event, speed, true));
                    server.broadcast(new ProjectileData(p.hashCode(), owner.getID(), d.getStart(), d.getTarget(), new Event(), speed));
                    return null;
                }
            });
        }
        
        private void DamageMessage(final DamageData d){
            server.broadcast(Filters.notEqualTo(connection), d);
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
        private void MoveMessage(final MoveData d){
            server.broadcast(Filters.notEqualTo(connection), d);
            app.enqueue(new Callable<Void>(){
                public Void call() throws Exception{
                    characterManager.updatePlayerLocation(d);
                    return null;
                }
            });
        }
        /**
         * Ping data is empty, simply used as a timing device.
         * @param d Data from the message.
         */
        private void PingMessage(PingData d){
            connection.send(d);
        }
        /**
         * Message recieved when a new Projectile is spawned.
         * <p>
         * Holds all data required to reproduce the projectile.
         * @param d The data of the message.
         */
        private void ProjectileMessage(ProjectileData d){
            //Util.log("[ServerNetwork] <ProjectileMessage> Projectile data recieved...", 2);
            final ProjectileData m = d;
            app.enqueue(new Callable<Void>(){
                public Void call() throws Exception{
                    ProjectileAttack attack = new ProjectileAttack(characterManager, m);
                    app.getWorld().addProjectile(attack);
                    server.broadcast(m);
                    return null;
                }
            });
        }
        private void SoundMessage(SoundData d){
            server.broadcast(d);
        }
        
        private void InventoryRequested(InventoryRequest d){
            //Inventory inv = characterManager.getPlayer(d.getID()).getInventory();
            InventoryResponse response = new InventoryResponse();
            //connection.send();
        }
        
        public void messageReceived(HostedConnection source, Message m) {
            connection = source;
            server = connection.getServer();
            
            // Handshaking
            if(m instanceof ConnectData){   // Version Checking
                ConnectMessage((ConnectData) m);
            }else if(m instanceof PlayerData){
                PlayerMessage((PlayerData) m);
            }
            // Quick actions
            else if(m instanceof ActionData){
                ActionMessage((ActionData) m);
            }else if(m instanceof DamageData){
                DamageMessage((DamageData) m);
            }else if(m instanceof MoveData){
                MoveMessage((MoveData) m);
            }else if(m instanceof PingData){
                PingMessage((PingData) m);
            }else if (m instanceof ProjectileData){
                ProjectileMessage((ProjectileData) m);
            }else if(m instanceof SoundData){
                SoundMessage((SoundData) m);
            }
            // Data requests
            else if(m instanceof InventoryRequest){
                InventoryRequested((InventoryRequest) m);
            }
        }
    }
}
