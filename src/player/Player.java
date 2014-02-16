package player;

import com.jme3.math.Vector2f;
import com.jme3.network.HostedConnection;
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
    protected boolean connected = false;
    
    public Player(){
        //
    }
    public Player(PlayerData d){
        // create w/ data
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
    
    // For networking
    public boolean isConnected(){
        return connected;
    }
    
    public void update(float tpf){
        //TBI
    }
    public void destroy(){
        connected = false;
    }
}
