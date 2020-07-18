package polarity.shared.entity;

import polarity.shared.character.GameCharacter;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import polarity.shared.character.types.CharType;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import polarity.shared.network.GameNetwork;
import polarity.shared.tools.Sys;
import polarity.shared.world.blocks.Block;
import polarity.shared.world.blocks.BlockData;
import polarity.shared.world.blocks.WallData;

/**
 * Entity - Represents any non-block object that is attached to the game polarity.shared.world.
 * @author Sindusk
 */
public abstract class Entity {
    protected GameCharacter owner;
    protected Node node = new Node("Root Entity Node");
    protected Node modelNode = new Node("Entity Model Node");
    protected Rectangle2D.Float bounds;
    protected Vector2f oldLoc = new Vector2f(0, 0);
    protected Vector2f newLoc = new Vector2f(0, 0);
    protected boolean destroyed = false;
    protected float radius = 0.7f;
    
    protected CharType type;
    
    protected float interp = 0; // Counter used for interpolation, so movement is smooth(er)
    
    public Entity(Node parent, GameCharacter owner){
        this.owner = owner;
        this.type = CharType.UNKNOWN;
        parent.attachChild(node); // Attaches the polarity.shared.entity node ("node") to the parent passed in
        node.attachChild(modelNode);
        this.bounds = new Rectangle2D.Float(newLoc.x-radius, newLoc.y-radius, radius*2, radius*2);
    }
    
    public void update(float tpf){
        Vector3f old3D = new Vector3f(oldLoc.x, oldLoc.y, 0);
        Vector3f new3D = new Vector3f(newLoc.x, newLoc.y, 0);
        node.setLocalTranslation(old3D.interpolateLocal(new3D, interp));
        if(interp < 1.0f){
            interp += tpf*GameNetwork.MOVE_INVERSE;
            if(interp > 1){
                interp = 1;
            }
        }
    }
    
    public Node getNode(){
        return node;
    }
    public GameCharacter getOwner(){
        return owner;
    }
    public CharType getType(){
        return type;
    }
    public Vector3f getLocalTranslation(){
        return new Vector3f(newLoc.x, newLoc.y, 0);
    }
    public Vector2f getLocation(){
        return newLoc.clone();
    }
    public Rectangle2D.Float getBounds(){
        return bounds;
    }
    public float getRadius(){
        return radius;
    }
    public boolean isDestroyed(){
        return destroyed;
    }
    
    public boolean canMove(Block block){
        if(block == null){
            return false;
        }
        BlockData d = block.getData();
        if(d instanceof WallData){
            return false;
        }
        return true;
    }
    
    public void moveLocation(Vector2f offset){
        this.oldLoc = newLoc.clone();
        this.newLoc = newLoc.add(offset);
        this.interp = 0;
        this.bounds = new Rectangle2D.Float(newLoc.x-radius, newLoc.y-radius, radius*2, radius*2);
    }
    public void updateLocation(Vector2f loc){
        if(Sys.getWorld().getBlock(loc) == null){
            return; // This ensures no errors occur if the client has not recieved the data for this area yet.
        }
        if(Sys.getWorld().getBlock(loc).getData() instanceof WallData){
            if(!(Sys.getWorld().getBlock(new Vector2f(loc.x, newLoc.y)).getData() instanceof WallData)){
                loc = new Vector2f(loc.x, newLoc.y);
            }else if(!(Sys.getWorld().getBlock(new Vector2f(newLoc.x, loc.y)).getData() instanceof WallData)){
                loc = new Vector2f(newLoc.x, loc.y);
            }else{
                return;
            }
        }
        this.oldLoc = newLoc.clone();
        this.newLoc = loc;
        this.interp = 0;
        this.bounds = new Rectangle2D.Float(newLoc.x-radius, newLoc.y-radius, radius*2, radius*2);
    }
    
    public void updateRotation(Vector2f loc){
        float dx = loc.x - newLoc.x;
        float dy = loc.y - newLoc.y;
        modelNode.setLocalRotation(new Quaternion().fromAngleAxis(FastMath.atan2(dy, dx), new Vector3f(0, 0, 1)));
    }
    
    public void move(float x, float y){
        newLoc.addLocal(x, y);
        node.move(x, y, 0);
    }
    public void move(Vector2f vector){
        this.move(vector.x, vector.y);
    }
    public void moveInstant(float x, float y){
        move(x, y);
        interp = 1;
    }
    public void moveInstant(Vector2f vector){
        moveInstant(vector.x, vector.y);
    }
    /**
     * Method used for checking and handling collisions.
     * <p>
     * The ArrayList that is passed in should have all *possible* collisions, and should not be filtered beforehand.
     * @param collisions ArrayList of the entities that could possibly collide with this one.
     * @return Returns the ArrayList of possible collisions *after* filtering.
     */
    public ArrayList<Entity> checkCollisions(GameNetwork network, ArrayList<Entity> collisions){
        int i = 0;
        while(i < collisions.size()){
            // Filters: This polarity.shared.entity (checking against itself), Distance/Radius
            if(this == collisions.get(i) || this.getLocation().distance(collisions.get(i).getLocation()) > radius){
                collisions.remove(i);
            }else{
                i++;
            }
        }
        return collisions;
    }
    
    public void destroy(){
        node.removeFromParent();
        destroyed = true;
    }
}
