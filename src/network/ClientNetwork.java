package network;

import com.jme3.audio.AudioNode;
import com.jme3.math.FastMath;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.network.Client;
import com.jme3.network.ClientStateListener;
import com.jme3.network.Message;
import com.jme3.network.MessageListener;
import com.jme3.network.Network;
import com.jme3.network.serializing.Serializer;
import java.io.IOException;
import java.util.concurrent.Callable;
import main.GameClient;
import netdata.*;
import player.PlayerManager;
import tools.Sys;
import tools.Util;

/**
 * ClientNetwork - Used for the connection and maintainence of client-side networking.
 * @author SinisteRing
 */
public class ClientNetwork{
    // Important variables:
    private ClientListener listener = new ClientListener();
    protected final GameClient app;
    protected Client client = null;  // For SpiderMonkey connectivity.
    
    // Game variables:
    protected PlayerManager playerManager;
    
    // Constants:
    public final float PING_INTERVAL = 1;
    public final float MOVE_INTERVAL = 0.05f;
    public final float MOVE_INVERSE = 1.0f/MOVE_INTERVAL;
    
    // ClientNetwork Variables:
    private boolean CLIENT_CONNECTED = false;
    private int     CLIENT_ID = -1;

    // Index Holders:
    private final int PING = 0;
    private final int MOVE = 1;

    // Instance Variables:
    private boolean pinging = false;
    private float[] timers = new float[2];
    private float time;
    
    public ClientNetwork(GameClient app){
        this.app = app;
        playerManager = new PlayerManager();
    }
    
    public boolean isConnected(){
        return CLIENT_CONNECTED;
    }
    public int getID(){
        return CLIENT_ID;
    }
    
    private void registerClass(Class c){
        Serializer.registerClass(c);
        client.addMessageListener(listener, c);
    }
    private void registerSerials(){
        for(NetData d : NetData.values()){
            registerClass(d.c);
        }
    }
    
    public boolean connect(String ip){
        try {
            client = Network.connectToServer(ip, 6143);
            registerSerials();
            client.addClientStateListener(listener);
            client.start();
            client.send(new ConnectData(Sys.getVersion()));
            timers[PING] = 1;
            timers[MOVE] = 0;
            return true;
        } catch (IOException ex) {
            Util.log(ex);
            return false;
        }
    }
    public void disconnect(){
        if(!CLIENT_CONNECTED){
            return;
        }
        CLIENT_ID = -1;
        CLIENT_CONNECTED = false;
        client.close();
    }
    public void close(){
        client.close();
    }
    public void update(float tpf){
        int i = 0;
        while(i < timers.length){
            timers[i] += tpf;
            i++;
        }

        // Ping:
        if(timers[PING] >= PING_INTERVAL && !pinging){
            time = Sys.getTimer().getTimeInSeconds();
            client.send(new PingData());
            timers[PING] = 0;
        }

        // Send updated movement data:
        if(timers[MOVE] >= MOVE_INTERVAL){
            client.send(new MoveData(CLIENT_ID, playerManager.getPlayer(CLIENT_ID).getLocation()));
            timers[MOVE] = 0;
        }
    }
    
    public void send(Message message){
        if(isConnected()){
            client.send(message);
        }
    }
    
    private class ClientListener implements MessageListener<Client>, ClientStateListener{
        private Client client;
        
        public void clientConnected(Client c){
            Util.log("Welcome to the server.");
        }
        public void clientDisconnected(Client c, DisconnectInfo info){
            Util.log(info.reason);
            // Go back to main screen
        }
        
        private void CommandMessage(CommandData d){
            final CommandData m = d;
            app.enqueue(new Callable<Void>(){
                public Void call() throws Exception{
                    Util.handleCommand(m.getCommand());
                    return null;
                }
            });
        }
        private void DisconnectMessage(DisconnectData d){
            final int id = d.getID();
            app.enqueue(new Callable<Void>(){
                public Void call() throws Exception{
                    //app.getPlayer(id).destroy();
                    playerManager.remove(id);
                    return null;
                }
            });
        }
        private void IDMessage(IDData d){
            CLIENT_ID = d.getID();
            client.send(new PlayerData(CLIENT_ID, new Vector2f(0, 0)));
        }
        private void MoveMessage(MoveData d){
            final MoveData m = d;
            app.enqueue(new Callable<Void>(){
                public Void call() throws Exception{
                    playerManager.updatePlayerLocation(m);
                    return null;
                }
            });
        }
        private void PingMessage(){
            final float pingTime = app.getTimer().getTimeInSeconds() - time;
            app.enqueue(new Callable<Void>(){
                public Void call() throws Exception{
                    Util.log("Ping: "+(int) FastMath.ceil(pingTime*1000)+" ms"); //Temporary
                    return null;
                }
            });
        }
        private void SoundMessage(SoundData d){
            if(d.getID() == CLIENT_ID) {
                return;
            }
            final String s = d.getSound();
            final Vector2f loc = playerManager.getPlayer(d.getID()).getLocation();
            app.enqueue(new Callable<Void>(){
                public Void call() throws Exception{
                    AudioNode node = new AudioNode(app.getAssetManager(), s);
                    node.setPositional(true);
                    node.setLocalTranslation(new Vector3f(loc.x, loc.y, 0));
                    node.playInstance();
                    return null;
                }
            });
        }
        // Player:
        private void PlayerMessage(PlayerData d){
            final PlayerData m = d;
            app.enqueue(new Callable<Void>(){
                public Void call() throws Exception{
                    playerManager.add(m);
                    if(m.getID() == CLIENT_ID){
                        // Connect player
                    }
                    return null;
                }
            });
        }

        public void messageReceived(Client source, Message m) {
            client = source;
            
            if(m instanceof CommandData){
                CommandMessage((CommandData) m);
            }else if(m instanceof DisconnectData){
                DisconnectMessage((DisconnectData) m);
            }else if(m instanceof IDData){
                IDMessage((IDData) m);
            }else if(m instanceof MoveData){
                MoveMessage((MoveData) m);
            }else if(m instanceof PingData){
                PingMessage();
            }else if(m instanceof SoundData){
                SoundMessage((SoundData) m);
            }else if(m instanceof PlayerData){
                PlayerMessage((PlayerData) m);
            }
        }
    }
}
