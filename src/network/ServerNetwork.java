package network;

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
import tools.Sys;
import tools.Util;

/**
 *
 * @author SinisteRing
 */
public class ServerNetwork{
    // Important variables:
    private ServerListener listener = new ServerListener();
    protected final GameServer app;
    protected Server server;
    protected ServerSettings settings = new ServerSettings();
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
    
    public void broadcast(Message m){
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
            server.broadcast(new DisconnectData(id));
            conn.close("Disconnected");
            Util.log("[Connection Removed] Player "+id+" has disconnected.");
        }
        
        /** Recieved when a player first pings the server, checking if a slot is open.
         * <p>
         * This method determines if the player has the correct version & if there's an open
         * slot in the server before telling the client it's a successful connection.
         * If the checks pass, this will send an ID back to the player for connection.
         * @param d Data from the message.
         */
        private void ConnectMessage(ConnectData d){
            Util.log("[ServerNetwork] <ConnectMessage> Recieving new connection...", 1);
            if(d.GetVersion().equals(app.getVersion())){
                app.enqueue(new Callable<Void>(){
                    public Void call() throws Exception{
                        int id = characterManager.findEmptyID();
                        if(id != -1){
                            //connection.send(new ServerStatusData(status));
                            connection.send(new IDData(id, true));
                        }
                        return null;
                    }
                });
            }else{
                Util.log("[Connect Message] ERROR: Client has incorrect version. A player was denied connection.");
                connection.close("Invalid Version. [Client: "+d.GetVersion()+"] [Server: "+app.getVersion()+"]");
            }
        }
        
        /** Recieved when a player moves.
         * <p>
         * This method is mainly used to broadcast the movement
         * @param d Data from the message.
         */
        private void MoveMessage(MoveData d){
            server.broadcast(Filters.notEqualTo(connection), d);
            final MoveData m = d;
            app.enqueue(new Callable<Void>(){
                public Void call() throws Exception{
                    characterManager.updatePlayerLocation(m);
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
            Util.log("[ServerNetwork] <ProjectileMessage> Projectile data recieved...");
            server.broadcast(Filters.notEqualTo(connection), d);
            final ProjectileData m = d;
            app.enqueue(new Callable<Void>(){
                public Void call() throws Exception{
                    Sys.getWorld().addProjectile(new ProjectileAttack(characterManager, m));
                    return null;
                }
            });
        }
        private void SoundMessage(SoundData d){
            server.broadcast(d);
        }
        /**
         * Message is recieved when a player is authenticated for joining the server.
         * <p>
         * This contains all the data for the player themselves, such as character stats, location, etc.
         * @param d The data of the message.
         */
        private void PlayerMessage(PlayerData d){
            Util.log("[ServerNetwork] <PlayerMessage> Player "+d.getID()+" (v"+app.getVersion()+") connected successfully.");
            int id = d.getID();
            characterManager.add(d);
            server.broadcast(Filters.notEqualTo(connection), d);
            characterManager.getPlayer(id).setConnection(connection);
            characterManager.sendData(connection);
        }
        
        public void messageReceived(HostedConnection source, Message m) {
            connection = source;
            server = connection.getServer();
            
            if(m instanceof ConnectData){
                ConnectMessage((ConnectData) m);
            }else if(m instanceof MoveData){
                MoveMessage((MoveData) m);
            }else if(m instanceof PingData){
                PingMessage((PingData) m);
            }else if (m instanceof ProjectileData){
                ProjectileMessage((ProjectileData) m);
            }else if(m instanceof SoundData){
                SoundMessage((SoundData) m);
            }else if(m instanceof PlayerData){
                PlayerMessage((PlayerData) m);
            }
        }
    }
}
