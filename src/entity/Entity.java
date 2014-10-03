package entity;

import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import java.util.ArrayList;
import network.ClientNetwork;
import tools.Sys;
import tools.Util;
import world.Chunk;
import world.World;

/**
 * Entity - Represents any non-block object that is attached to the game world.
 * @author Sindusk
 */
public abstract class Entity {
    protected Node node = new Node("Entity");
    protected Vector2f oldLoc = new Vector2f(0, 0);
    protected Vector2f newLoc = new Vector2f(0, 0);
    protected float length; //half length
    protected float height; //total height
    
    private float interp = 0;   // Counter used for interpolation, so movement is smooth(er)
    
    public Entity(Node parent){
        parent.attachChild(node); // Attaches the entity node ("node") to the parent passed in
    }
    
    public void update(float tpf){
        Vector3f old3D = new Vector3f(oldLoc.x, oldLoc.y, 0);
        Vector3f new3D = new Vector3f(newLoc.x, newLoc.y, 0);
        node.setLocalTranslation(old3D.interpolate(new3D, interp));
        if(Sys.debug > 3){
            Util.log("[Entity] <update> interpolation = "+old3D.interpolate(new3D, interp).toString()+" - interp = "+interp);
        }
        if(interp < 1.0f){
            if(Sys.debug > 4){
                Util.log("[Entity] <update> tpf*MOVE_INVERSE = "+tpf*ClientNetwork.MOVE_INVERSE);
            }
            interp += tpf*ClientNetwork.MOVE_INVERSE;
        }else{
            if(Sys.debug > 5){
                Util.log("Late message for movement!");
            }
        }
    }
    
    public ArrayList<Chunk> intersectingChunks(World which){
        ArrayList<Chunk> myChunks=new ArrayList<Chunk>();
        // implement
        return myChunks;
    }
    public Vector2f getLocation(){
        Util.log("[Entity] <getLocation> location = "+newLoc.toString(), 3);
        return newLoc.clone();
    }
    public Vector2f getBottomLeft(){
        return new Vector2f(newLoc.x-length, newLoc.y);
    }
    public Vector2f getTopRight(){
        return new Vector2f(newLoc.x+length, newLoc.y+height);
    }
    
    public void updateLocation(Vector2f loc){
        Util.log("[Entity] <setLocation> OLD locs = "+oldLoc.toString()+" - "+newLoc.toString(), 4);
        this.oldLoc = newLoc.clone();
        this.newLoc = loc;
        Util.log("[Entity] <setLocation> NEW locs = "+oldLoc.toString()+" - "+newLoc.toString(), 4);
        this.interp = 0;
    }
    
    public void updateRotation(Vector2f loc){
        float dx = loc.x - newLoc.x;
        float dy = loc.y - newLoc.y;
        node.setLocalRotation(new Quaternion().fromAngleAxis(FastMath.atan2(dy, dx), new Vector3f(0, 0, 1)));
    }
    
    public void move(float x, float y){
        Util.log("[Entity] <move> OLD newLoc = "+newLoc.toString(), 3);
        Util.log("[Entity] <move> OLD Node = "+node.getLocalTranslation().toString(), 3);
        newLoc.addLocal(x, y);
        node.move(x, y, 0);
        Util.log("[Entity] <move> NEW newLoc = "+newLoc.toString(), 3);
        Util.log("[Entity] <move> NEW Node = "+node.getLocalTranslation().toString(), 3);
    }
    public void move(Vector2f vector){
        this.move(vector.x, vector.y);
    }
    
    //Returns the area of the intersection of two rectangles.  Does not tell us which parts intersected.
    public float intersects(Vector2f bottomLeftCorner, Vector2f topRightCorner) {
        Vector2f tempVec=getBottomLeft();
        float x0=tempVec.x;
        float y0=tempVec.y;
        tempVec=getTopRight();
        float x1=tempVec.x;
        float y1=tempVec.y;

        if (!(x0 > topRightCorner.x || x1 < bottomLeftCorner.x || y0 > topRightCorner.y || y1 < bottomLeftCorner.y)) {
            float a;
            float b;
            float c;
            float d;
            if (bottomLeftCorner.x < x0) {
                a = x0;
            } else {
                a = bottomLeftCorner.x;
            }
            if (topRightCorner.x > x1) {
                b = x1;
            } else {
                b = topRightCorner.x;
            }
            if (bottomLeftCorner.y < y0) {
                c = y0;
            } else {
                c = bottomLeftCorner.y;
            }
            if (topRightCorner.y > y1) {
                d = y1;
            } else {
                d = topRightCorner.y;
            }
            return (b - a) * (d - c);
        }
        return 0;
    }
}
