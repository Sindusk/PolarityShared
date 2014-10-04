package character;

import action.ProjectileAttack;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.network.HostedConnection;
import com.jme3.network.serializing.Serializable;
import com.jme3.scene.Node;
import items.Equipment;
import items.Weapon;
import netdata.PlayerData;
import netdata.ProjectileData;
import screens.Screen;
import tools.Sys;
import tools.Util;
import types.AttackType;

/**
 * Player class holds all data player-related.
 * @author Sindusk
 */
@Serializable
public class Player extends GameCharacter{
    protected Equipment equipment;
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
        entity = Sys.getWorld().addPlayerEntity(ColorRGBA.Orange);
        equipment = d.getEquipment();
        this.data = d;
    }
    
    public HostedConnection getConnection(){
        return conn;
    }
    public PlayerData getData(){
        return data;
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
            return;
        }
        if(weapon.getAttackType() == AttackType.Charged){
            //chargeAttack(weapon, cursorLoc, down);
            Util.log("Error: Charged not yet implemented.");
        }else if(down){
            Util.log("[Player] <attack> Creating new ProjectileAttack...", 2);
            Vector3f worldTarget = Util.getWorldLoc(cursorLoc, Sys.getCamera());
            ProjectileAttack a = new ProjectileAttack(this, getLocation(), new Vector2f(worldTarget.x, worldTarget.y), weapon, down){
                @Override
                public void onCollide(){
                    //implement
                }
            };
            Sys.getWorld().addProjectile(a);
            Screen.getClientNetwork().send(new ProjectileData(((Player)a.getOwner()).getData().getID(), a.getStart(), a.getTarget(), weapon));
            Util.log("[Player] <attack> Sent ProjectileAttack to server.", 2);
        }
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
        entity.updateLocation(entity.getLocation().add(move));
    }
    
    public void destroy(){
        connected = false;
    }
}
