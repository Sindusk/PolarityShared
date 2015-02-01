package character;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.network.HostedConnection;
import com.jme3.network.serializing.Serializable;
import com.jme3.scene.Node;
import items.Equipment;
import items.Inventory;
import items.Weapon;
import netdata.ActionData;
import netdata.PlayerData;
import screens.Screen;
import stats.advanced.Vitals;
import tools.Sys;
import tools.Util;
import types.AttackType;

/**
 * Player class holds all data player-related.
 * @author Sindusk
 */
@Serializable
public class Player extends GameCharacter{
    protected int id;
    protected Vitals vitals;
    protected Inventory inventory;
    protected Equipment equipment;
    protected PlayerData data;
    protected HostedConnection conn;
    protected Vector2f mousePos;
    protected String name;
    protected float gcd = 0;    // Internal - actual storage of current global cooldown.
    protected boolean attacking = false;
    protected boolean[] movement = new boolean[4];  // Up, Right, Down, Left
    protected boolean connected = false;
    
    protected float globalCooldown = 0.5f;  // Player's global cooldown value
    
    public Player(Node node, PlayerData d){
        connected = true;
        id = d.getID();
        int i = 0;
        while(i < movement.length){
            movement[i] = false;
            i++;
        }
        name = d.getName();
        //inventory = d.getInventory();
        inventory = new Inventory();
        equipment = d.getEquipment();
        vitals = new Vitals(id);
        this.data = d;
    }
    public void create(){
        entity = Sys.getWorld().addPlayerEntity(this, ColorRGBA.Orange);
    }
    
    public int getID(){
        return id;
    }
    public HostedConnection getConnection(){
        return conn;
    }
    public PlayerData getData(){
        return data;
    }
    public String getName(){
        return name;
    }
    public Inventory getInventory(){
        return inventory;
    }
    public Vitals getVitals(){
        return vitals;
    }
    
    public void setAttacking(boolean down){
        this.attacking = down;
    }
    public void setConnection(HostedConnection conn){
        this.conn = conn;
    }
    public void set3DMouseLocation(Vector2f loc){
        this.mousePos = loc;
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
    
    public void attack(Vector2f cursorLoc){
        Weapon weapon = equipment.getWeapon();
        if(weapon == null){
            Util.log("Error: No weapon ["+id+"]");
            return;
        }
        if(weapon.getAttackType() == AttackType.Charged){
            //chargeAttack(weapon, cursorLoc, down);
            Util.log("Error: Charged not yet implemented.");
        }else{
            Util.log("[Player] <attack> Creating new ProjectileAttack...", 2);
            Vector3f worldTarget = Util.getWorldLoc(cursorLoc, Sys.getCamera());    // Analyzes where in world space the player clicked.
            Vector2f target = new Vector2f(worldTarget.x, worldTarget.y);
            Screen.getClientNetwork().send(new ActionData(getID(), getLocation(), target));
            Util.log("[Player] <attack> Sent ProjectileAttack to server.", 2);
        }
    }
    
    public void updateLocal(float tpf, Vector2f cursorLoc){
        if(attacking && gcd <= 0){
            attack(cursorLoc);
            gcd += globalCooldown;
        }else{
            if(gcd > 0){
                gcd -= tpf;
            }
        }
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
    
    public void damage(float value){
        vitals.damage(value);
    }
    
    public void destroy(){
        entity.destroy();
        connected = false;
    }
}
