package network;

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
import player.PlayerManager;
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
    
    // Game variables:
    protected PlayerManager playerManager = new PlayerManager();
    
    public ServerNetwork(GameServer app){
        this.app = app;
        try {
            server = Network.createServer(6143);
            listener = new ServerListener();
            registerSerials();
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
            int id = playerManager.remove(conn);
            if(id == -1){
                return;
            }
            server.broadcast(new DisconnectData(id));
            conn.close("Disconnected");
            Util.log("[Connection Removed] Player "+id+" has disconnected.");
        }
        
        // Recieved when a player first pings the server, checking if a slot is open.
        // This method determines if the player has the correct version & if there's an open
        // slot in the server before telling the client it's a successful connection.
        // If the checks pass, this will send an ID back to the player for connection.
        private void ConnectMessage(ConnectData d){
            Util.log("[Connect Message] Connecting new player...");
            if(d.GetVersion().equals(app.getVersion())){
                app.enqueue(new Callable<Void>(){
                    public Void call() throws Exception{
                        int id = playerManager.findEmptyID();
                        if(id == -1){
                            Util.log("[Connect Message] ERROR: Server full. Player was denied connection.");
                        }else{
                            connection.send(new IDData(id, false));
                        }
                        return null;
                    }
                });
            }else{
                Util.log("[Connect Message] ERROR: Client has incorrect version. Player "+d.getID()+" was denied connection.");
                connection.close("Invalid Version.");
            }
        }
        private void MoveMessage(MoveData d){
            server.broadcast(Filters.notEqualTo(connection), d);
            final MoveData m = d;
            app.enqueue(new Callable<Void>(){
                public Void call() throws Exception{
                    playerManager.updatePlayerLocation(m);
                    return null;
                }
            });
        }
        private void PingMessage(PingData d){
            connection.send(d);
        }
        private void SoundMessage(SoundData d){
            //System.out.println("Handling SoundData from client "+d.getID());
            server.broadcast(d);
        }
        // Players:
        private void PlayerMessage(PlayerData d){
            Util.log("Player "+d.getID()+" [Version "+app.getVersion()+"] connected successfully.");
            int id = d.getID();
            playerManager.add(d);
            server.broadcast(d);
            playerManager.getPlayer(id).setConnection(connection);
            playerManager.sendData(connection);
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
            }else if(m instanceof SoundData){
                SoundMessage((SoundData) m);
            }else if(m instanceof PlayerData){
                PlayerMessage((PlayerData) m);
            }
        }
    }
}
