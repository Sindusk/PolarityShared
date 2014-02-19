package player;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.network.HostedConnection;
import com.jme3.scene.Node;
import entity.PlayerEntity;
import netdata.PlayerData;

/**
 *
 * @author SinisteRing
 */
public class Player {
    protected PlayerEntity entity;
    protected PlayerData data;
    protected HostedConnection conn;
    protected boolean[] movement = new boolean[4];  // Up, Right, Down, Left
    protected boolean connected = false;
    
    public Player(Node node, PlayerData d){
        connected = true;
        int i = 0;
        while(i < movement.length){
            movement[i] = false;
            i++;
        }
        entity = new PlayerEntity(node, ColorRGBA.Orange);
    }
    
    public HostedConnection getConnection(){
        return conn;
    }
    public PlayerData getData(){
        return data;
    }
    public Vector2f getLocation(){
        return entity.getLocation();
    }
    
    public void setConnection(HostedConnection conn){
        this.conn = conn;
    }
    public void setLocation(Vector2f loc){
        entity.setLocation(loc);
    }
    public void setMovement(int dir, boolean value){
        movement[dir] = value;
    }
    
    // For networking
    public boolean isConnected(){
        return connected;
    }
    
    public void update(float tpf){
        Vector2f move = new Vector2f(0, 0);
        tpf *= 5; //Temporary, for scaling.
        if(movement[0]){
            move.y += tpf;
        }
        if(movement[1]){
            move.x += tpf;
        }
        if(movement[2]){
            move.y -= tpf;
        }
        if(movement[3]){
            move.x -= tpf;
        }
        entity.move(move);
    }
    public void destroy(){
        connected = false;
    }
}
