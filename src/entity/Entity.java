package entity;

import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import network.ClientNetwork;
import tools.Sys;
import tools.Util;
import world.blocks.WallData;

/**
 * Entity - Represents any non-block object that is attached to the game world.
 * @author Sindusk
 */
public abstract class Entity {
    protected Node node = new Node("Entity");
    protected Rectangle2D.Float bounds;
    protected Vector2f oldLoc = new Vector2f(0, 0);
    protected Vector2f newLoc = new Vector2f(0, 0);
    protected boolean destroyed = false;
    protected float radius = 0.7f;
    protected float length; //half length
    protected float height; //total height
    
    protected float interp = 0; // Counter used for interpolation, so movement is smooth(er)
    
    public Entity(Node parent){
        Util.log("[Entity] <Constructor> Creating entity: "+this.toString(), 2);
        parent.attachChild(node); // Attaches the entity node ("node") to the parent passed in
        this.bounds = new Rectangle2D.Float(newLoc.x-radius, newLoc.y-radius, radius*2, radius*2);
    }
    
    public void update(float tpf){
        Vector3f old3D = new Vector3f(oldLoc.x, oldLoc.y, 0);
        Vector3f new3D = new Vector3f(newLoc.x, newLoc.y, 0);
        node.setLocalTranslation(old3D.interpolate(new3D, interp));
        Util.log("[Entity] <update> interpolation = "+old3D.interpolate(new3D, interp).toString()+" - interp = "+interp, 3);
        if(interp < 1.0f){
            Util.log("[Entity] <update> tpf*MOVE_INVERSE = "+tpf*ClientNetwork.MOVE_INVERSE, 4);
            interp += tpf*ClientNetwork.MOVE_INVERSE;
        }else{
            Util.log("Late message for movement!", 5);
        }
    }
    
    public Vector2f getLocation(){
        Util.log("[Entity] <getLocation> location = "+newLoc.toString(), 3);
        return newLoc.clone();
    }
    public Rectangle2D.Float getBounds(){
        return bounds;
    }
    public Vector2f getBottomLeft(){
        return new Vector2f(newLoc.x-length, newLoc.y);
    }
    public Vector2f getTopRight(){
        return new Vector2f(newLoc.x+length, newLoc.y+height);
    }
    public float getRadius(){
        return radius;
    }
    public boolean isDestroyed(){
        return destroyed;
    }
    
    public void moveLocation(Vector2f offset){
        Util.log("[Entity] <updateLocation> OLD locs = "+oldLoc.toString()+" - "+newLoc.toString(), 4);
        this.oldLoc = newLoc.clone();
        this.newLoc = newLoc.add(offset);
        this.interp = 0;
        this.bounds = new Rectangle2D.Float(newLoc.x-radius, newLoc.y-radius, radius*2, radius*2);
        Util.log("[Entity] <updateLocation> NEW locs = "+oldLoc.toString()+" - "+newLoc.toString(), 4);
    }
    public void updateLocation(Vector2f loc){
        Util.log("[Entity] <updateLocation> OLD locs = "+oldLoc.toString()+" - "+newLoc.toString(), 4);
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
        Util.log("[Entity] <updateLocation> NEW locs = "+oldLoc.toString()+" - "+newLoc.toString(), 4);
    }
    
    public void updateRotation(Vector2f loc){
        float dx = loc.x - newLoc.x;
        float dy = loc.y - newLoc.y;
        node.setLocalRotation(new Quaternion().fromAngleAxis(FastMath.atan2(dy, dx), new Vector3f(0, 0, 1)));
    }
    
    public void move(float x, float y){
        newLoc.addLocal(x, y);
        node.move(x, y, 0);
    }
    public void move(Vector2f vector){
        this.move(vector.x, vector.y);
    }
    /**
     * Method used for checking and handling collisions.
     * <p>
     * The ArrayList that is passed in should have all *possible* collisions, and should not be filtered beforehand.
     * @param collisions ArrayList of the entities that could possibly collide with this one.
     * @return Returns the ArrayList of possible collisions *after* filtering.
     */
    public ArrayList<Entity> checkCollisions(ArrayList<Entity> collisions){
        int i = 0;
        while(i < collisions.size()){
            // Filters: This entity (checking against itself), Distance/Radius
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
