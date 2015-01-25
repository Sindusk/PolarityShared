package network;

import action.ProjectileAttack;
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
import com.jme3.scene.Node;
import input.InputHandler;
import items.Equipment;
import items.Weapon;
import java.io.IOException;
import java.util.concurrent.Callable;
import main.GameClient;
import netdata.*;
import character.CharacterManager;
import netdata.destroyers.DestroyProjectileData;
import screens.GameScreen;
import screens.MenuScreen;
import screens.MultiplayerScreen;
import screens.Screen;
import tools.Sys;
import tools.Util;

/**
 * ClientNetwork - Used for the connection and maintainence of client-side networking.
 * @author SinisteRing
 */
public class ClientNetwork extends GameNetwork{
    // Important variables:
    private ClientListener listener = new ClientListener();
    protected final GameClient app;
    protected Client client = null;  // For SpiderMonkey connectivity.
    
    // Game variables:
    protected InputHandler inputHandler;
    protected CharacterManager charManager;
    protected ServerStatus serverStatus;
    
    // Constants:
    public static final float PING_INTERVAL = 1;
    public static final float MOVE_INTERVAL = 0.05f;
    public static final float MOVE_INVERSE = 1.0f/MOVE_INTERVAL;
    
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
    
    public ClientNetwork(GameClient app, Node root, Node gui){
        Util.log("[ClientNetwork] <Initialize> Initializing ClientNetwork...", 2);
        this.app = app;
        this.charManager = app.getCharManager();
    }
    
    // Attempted connection, will fail if the server is not available.
    public boolean connect(String ip){
        try {
            client = Network.connectToServer(ip, 6143);
            registerSerials();
            client.addClientStateListener(listener);
            client.start();
            client.send(new ConnectData(Sys.getVersion(), "Sindusk"));
            timers[PING] = 1;
            timers[MOVE] = 0;
            return true;
        } catch (IOException ex) {
            Util.log(ex);
            return false;
        }
    }
    
    // Primary update loop
    public void update(float tpf){
        if(!client.isConnected()){
            return;
        } 
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
            Util.log("[ClientNetwork] <update> Sending my ("+CLIENT_ID+") movement...", 4);
            client.send(new MoveData(CLIENT_ID, charManager.getPlayer(CLIENT_ID).getLocation(), inputHandler.get3DCursorLocation()));
            timers[MOVE] = 0;
        }
    }
    
    public boolean isConnected(){
        return CLIENT_CONNECTED;
    }
    public int getID(){
        Util.log("[ClientNetwork] <getID> CLIENT_ID = "+CLIENT_ID, 4);
        return CLIENT_ID;
    }
    public void setInputHandler(InputHandler inputHandler){
        this.inputHandler = inputHandler;
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
    
    public void disconnect(){
        if(!CLIENT_CONNECTED){
            return;
        }
        CLIENT_ID = -1;
        CLIENT_CONNECTED = false;
        client.close();
    }
    
    public void send(Message message){
        if(isConnected()){
            Util.log("[ClientNetwork] <send> Sending message...", 2);
            client.send(message);
        }
    }
    public void stop(){
        client.close();
    }
    
    private class ClientListener implements MessageListener<Client>, ClientStateListener{
        private Client client;
        
        public void clientConnected(Client c){
            Util.log("Connection to server successful.");
        }
        public void clientDisconnected(Client c, DisconnectInfo info){
            if(info != null){
                Util.log(info.reason);
            }
            inputHandler.switchScreens(new MenuScreen(app, Screen.getTopRoot(), Screen.getTopGUI()));
        }
        
        // HANDSHAKE PROCESS BEGINS
        
        // SSPD on  - Recieves player data + id, let the server know you're ready for world data
        private void PlayerConnectionMessage(final PlayerConnectionData d){
            Util.log("[ClientNetwork] <PlayerConnectionMessage> Receiving PlayerConnectionMessage...", 4);
            CLIENT_ID = d.getID();
            client.send(d.getPlayerData());
            app.enqueue(new Callable<Void>(){
                public Void call() throws Exception{
                    inputHandler.switchScreens(new GameScreen(app, Screen.getTopRoot(), Screen.getTopGUI()));
                    charManager.add(d.getPlayerData());
                    CLIENT_CONNECTED = true;
                    return null;
                }
            });
        }
        
        // SSPD off - Recieves player ID, must send data back to server with player data
        /** When the server has determined the players ID, this message sends it to the player.
         * <p>
         * This is also only recieved if the player connects successfully, so it is best used
         * to actually begin entering the game and starting play.
         * @param d The data from the message.
         */
        private void PlayerIDMessage(final PlayerIDData d){
            Util.log("[ClientNetwork] <PlayerIDMessage> Recieving PlayerIDMessage...", 4);
            CLIENT_ID = d.getID();
            final PlayerData pd = new PlayerData(CLIENT_ID, "Player "+CLIENT_ID, new Vector2f(0, 0), new Equipment(new Weapon()));
            client.send(pd);
            app.enqueue(new Callable<Void>(){
                public Void call() throws Exception{
                    inputHandler.switchScreens(new GameScreen(app, Screen.getTopRoot(), Screen.getTopGUI()));
                    charManager.add(pd);
                    CLIENT_CONNECTED = true;
                    return null;
                }
            });
        }
        
        private void ChunkMessage(ChunkData d){
            final ChunkData m = d;
            app.enqueue(new Callable<Void>(){
                public Void call() throws Exception{
                    app.updateChunk(m);
                    return null;
                }
            });
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
        
        private void DamageMessage(DamageData d){
            final DamageData m = d;
            app.enqueue(new Callable<Void>(){
                public Void call() throws Exception{
                    charManager.damagePlayer(m);
                    return null;
                }
            });
        }
        
        /** This is the message sent when the player is disconnected.
         * <p>
         * It holds certain data for the disconnect message, and potentially other misc data.
         * @param d The data from the message.
         */
        private void DisconnectMessage(DisconnectData d){
            Util.log("[ClientNetwork] <DisconnectMessage> Recieving DisconnectMessage...", 1);
            final int id = d.getID();
            app.enqueue(new Callable<Void>(){
                public Void call() throws Exception{
                    charManager.remove(id);
                    return null;
                }
            });
        }
        
        /** When a player moves to a new position, this message is recieved to update all clients of the player's new position.
         * <p>
         * The client will interpet the interpolation over time to
         * the new location, but this will give the final location
         * of where they're at on the server.
         * @param d The data from the message.
         */
        private void MoveMessage(MoveData d){
            Util.log("[ClientNetwork] <MoveMessage> Recieving MoveMessage...", 4);
            final MoveData m = d;
            app.enqueue(new Callable<Void>(){
                public Void call() throws Exception{
                    charManager.updatePlayerLocation(m);
                    return null;
                }
            });
        }
        private void PingMessage(){
            final float pingTime = app.getTimer().getTimeInSeconds() - time;
            app.enqueue(new Callable<Void>(){
                public Void call() throws Exception{
                    Util.log("[ClientNetwork] <PingMessage> Current Ping: "+(int) FastMath.ceil(pingTime*1000)+" ms", 4);
                    return null;
                }
            });
        }
        
        /**
         * Message responsible for updating all clients of a new player's information.
         * <p>
         * Contains an instance of Player to give to all clients, so they have client-side
         * knowledge of what to do when that player makes an action.
         * @param d The data from the message.
         */
        private void PlayerMessage(PlayerData d){
            Util.log("[ClientNetwork] <PlayerMessage> Recieving new PlayerMessage...", 2);
            if(d.getID() == CLIENT_ID){
                return;
            }
            final PlayerData m = d;
            app.enqueue(new Callable<Void>(){
                public Void call() throws Exception{
                    charManager.add(m);
                    return null;
                }
            });
        }
        
        /**
         * Message broadcast when a projectile is spawned.
         * <p>
         * Holds all information necessary to spawn the projectile.
         * @param d The data from the message.
         */
        private void ProjectileMessage(ProjectileData d){
            Util.log("[ClientNetwork] <ProjectileMessage> Recieving new ProjectileMessage...", 2);
            final ProjectileData m = d;
            app.enqueue(new Callable(){
                public Void call() throws Exception{
                    Sys.getWorld().addProjectile(new ProjectileAttack(charManager, m));
                    return null;
                }
            });
        }
        
        /** Server information & status message.
         * <p>
         * The server sends this data to inform the client of crucial
         * information before allowing entry to the server.
         * @param d The data from the message.
         */
        private void ServerStatusMessage(ServerStatusData d){
            Util.log("[ClientNetwork] <ServerStatusMessage> Recieving new ServerStatusMessage...", 2);
            final ServerStatusData m = d;
            app.enqueue(new Callable(){
                public Void call() throws Exception{
                    if(!(inputHandler.getScreen() instanceof MultiplayerScreen)){
                        Util.log("Error 5: Server sent status when multiplayer screen was not open.", 0);
                    }
                    serverStatus = m.getStatus();
                    return null;
                }
            });
        }
        
        // Recieved when a sound is played in the server world, and the client needs to know.
        private void SoundMessage(SoundData d){
            if(d.getID() == CLIENT_ID) {
                return;
            }
            final String s = d.getSound();
            final Vector2f loc = charManager.getPlayer(d.getID()).getLocation();
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
        
        private void DestroyProjectile(DestroyProjectileData d){
            final int hashCode = d.getHashCode();
            app.enqueue(new Callable<Void>(){
                public Void call() throws Exception{
                    Sys.getWorld().destroyProjectile(hashCode);
                    return null;
                }
            });
        }
        
        // Handler for when any message (generic) is recieved.
        // It then separates it by type and calls the proper sub-method to handle each instance.
        public void messageReceived(Client source, Message m) {
            client = source;
            
            // Handshaking
            if(m instanceof PlayerConnectionData){
                PlayerConnectionMessage((PlayerConnectionData) m);
            }else if(m instanceof PlayerIDData){
                PlayerIDMessage((PlayerIDData) m);
            }
            // Quick actions and creation messages
            else if(m instanceof ChunkData){
                ChunkMessage((ChunkData) m);
            }else if(m instanceof CommandData){
                CommandMessage((CommandData) m);
            }else if(m instanceof DamageData){
                DamageMessage((DamageData) m);
            }else if(m instanceof DisconnectData){
                DisconnectMessage((DisconnectData) m);
            }else if(m instanceof MoveData){
                MoveMessage((MoveData) m);
            }else if(m instanceof PingData){
                PingMessage();
            }else if(m instanceof PlayerData){
                PlayerMessage((PlayerData) m);
            }else if(m instanceof ProjectileData){
                ProjectileMessage((ProjectileData) m);
            }else if(m instanceof ServerStatusData){
                ServerStatusMessage((ServerStatusData) m);
            }else if(m instanceof SoundData){
                SoundMessage((SoundData) m);
            }
            // Data cleanup/destruction messages
            else if(m instanceof DestroyProjectileData){
                DestroyProjectile((DestroyProjectileData) m);
            }
        }
    }
}
