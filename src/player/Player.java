package player;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.network.HostedConnection;
import com.jme3.scene.Node;
import entity.PlayerEntity;
import items.Equipment;
import items.Weapon;
import netdata.PlayerData;
import tools.Util;
import types.AttackType;

/**
 *
 * @author SinisteRing
 */
public class Player {
    protected Equipment equipment;
    protected PlayerEntity entity;
    protected PlayerData data;
    protected HostedConnection conn;
    protected Vector2f mousePos;
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
        equipment = d.getEquipment();
        this.data = d;
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
    public void setMousePosition(Vector2f mousePos){
        this.mousePos = mousePos;
    }
    public void updateLocation(Vector2f loc){
        entity.updateLocation(loc);
    }
    public void updateRotation(Vector2f loc){
        this.mousePos = loc.clone();
        entity.updateRotation(loc);
    }
    public void setMovement(int dir, boolean value){
        movement[dir] = value;
    }
    
    // For networking
    public boolean isConnected(){
        return connected;
    }
    
    public void attack(Vector2f cursorLoc, boolean down){
        Weapon weapon = equipment.getWeapon();
        if(weapon == null){
            Util.log("Error: No weapon ["+data.getID()+"]");
        }
        /*if(weapon.getAttackType() == AttackType.Charged){
            //chargeAttack(weapon, cursorLoc, down);
        }else if(down){
            //fireAttack(weapon, cursorLoc, down);
        }*/
    }
    
    public void updateMovement(float tpf){
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
        entity.updateRotation(mousePos);
        entity.move(move);
    }
    
    public void update(float tpf){
        entity.update(tpf);
    }
    
    public void destroy(){
        connected = false;
    }
}
