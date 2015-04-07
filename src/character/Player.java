package character;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.network.HostedConnection;
import com.jme3.scene.Node;
import items.data.equipment.WeaponItemData;
import netdata.ActionData;
import netdata.updates.MatrixUpdate;
import character.data.PlayerData;
import screens.Screen;
import spellforge.SpellMatrix;
import stats.advanced.Resources;
import tools.Sys;
import tools.Util;
import tools.Vector2i;
import world.World;

/**
 * Player class holds all data player-related.
 * @author Sindusk
 */
public class Player extends LivingCharacter{
    protected SpellMatrix[] matrix = new SpellMatrix[4];
    protected Resources resources = new Resources();
    protected PlayerData data;
    protected HostedConnection conn;
    
    protected Vector2i chunkKey;    // Current chunk coordinates for player position.
    protected float gcd = 0;    // Internal - actual storage of current global cooldown.
    protected int attackIndex = 0;
    protected boolean attacking = false;
    protected boolean[] movement = new boolean[4];  // Up, Right, Down, Left
    protected boolean connected = false;
    protected float globalCooldown = 0.25f;  // Player's global cooldown value
    
    public Player(PlayerData d){
        super(d.getID(), d.getName());
        this.data = d;
        connected = true;
        int i = 0;
        while(i < movement.length){
            movement[i] = false;
            i++;
        }
    }
    public void createEntity(World world){
        entity = world.addPlayerEntity(this, ColorRGBA.Orange);
    }
    
    public Resources getResources(){
        return resources;
    }
    public Vector2i getChunkKey(){
        return chunkKey;
    }
    public HostedConnection getConnection(){
        return conn;
    }
    public PlayerData getData(){
        return data;
    }
    public SpellMatrix[] getMatrixArray(){
        return matrix;
    }
    public SpellMatrix getMatrix(int index){
        return matrix[index];
    }
    
    public void setChunkKey(Vector2i chunkKey){
        this.chunkKey = chunkKey;
    }
    public void setAttacking(int attackIndex, boolean down){
        this.attackIndex = attackIndex;
        this.attacking = down;
    }
    public void setConnection(HostedConnection conn){
        this.conn = conn;
    }
    public void setMatrix(int index, SpellMatrix matrix){
        this.matrix[index] = matrix;
    }
    
    public void updateMatrix(MatrixUpdate d){
        matrix[d.getSlot()].changeData(d);
    }
    public void updateLocation(Vector2f loc){
        entity.updateLocation(loc);
    }
    public void updateRotation(Vector2f loc){
        entity.updateRotation(loc);
    }
    public void setMovement(int dir, boolean value){
        movement[dir] = value;
    }
    
    @Override
    public boolean isEnemy(GameCharacter other){
        if(other instanceof Monster){
            return true;
        }
        return false;
    }
    // For networking
    public boolean isConnected(){
        return connected;
    }
    
    public void initializeMatrixArray(Node parent){
        int i = 0;
        while(i < matrix.length){
            matrix[i] = new SpellMatrix(parent, this, new Vector2f(Sys.width*0.5f, Sys.height*0.55f), i+6, i+6);
            matrix[i].setVisible(false);
            i++;
        }
    }
    
    public void attack(Vector2f cursorLoc){
        WeaponItemData weapon = data.getEquipment().getWeapon();
        if(weapon == null){
            Util.log("Error: No weapon ["+id+"]");
            return;
        }
        Vector3f worldTarget = Util.getWorldLoc(cursorLoc, Sys.getCamera());    // Analyzes where in world space the player clicked.
        Vector2f target = new Vector2f(worldTarget.x, worldTarget.y);
        Screen.getClientNetwork().send(new ActionData(getID(), attackIndex, getLocation(), target));
    }
    
    @Override
    public void serverUpdate(float tpf){
        super.serverUpdate(tpf);
        updateResources(tpf);
        for(SpellMatrix spellMatrix : matrix){
            if(spellMatrix != null){
                spellMatrix.update(tpf);
            }
        }
    }
    
    public void updateResources(float tpf){
        resources.update(tpf);
    }
    public void updateLocal(float tpf, Vector2f cursorLocWorld, Vector2f cursorLoc){
        // Attacking & Global Cooldown Calculations
        if(attacking && gcd <= 0){
            attack(cursorLoc);
            gcd += globalCooldown;
        }else{
            if(gcd > 0){
                gcd -= tpf;
                if(gcd < 0){
                    gcd = 0;
                }
            }
        }
        
        // Movement Calculations
        Vector2f move = new Vector2f(0, 0);
        tpf *= charStats.getMovementSpeed(); //Temporary, for scaling.
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
        
        // Apply movement & rotation to entity
        entity.updateRotation(cursorLocWorld);
        entity.updateLocation(entity.getLocation().add(move));
    }
    
    public void destroy(){
        entity.destroy();
        connected = false;
    }
}
