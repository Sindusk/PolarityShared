package entity;

import com.jme3.math.Vector2f;
import com.jme3.scene.Node;
import java.util.ArrayList;
import world.Chunk;
import world.World;

/**
 * Entity - Represents any non-block object that is attached to the game world.
 * @author SinisteRing
 */
public abstract class Entity {
    protected Node node = new Node("Entity");
    protected Vector2f velocity=new Vector2f(0, 0);
    protected Vector2f position=new Vector2f(0, 0);
    protected float length; //half length
    protected float height; //total height
    protected float mass;
    boolean grounded;
    
    
    public Entity(Node parent){
        parent.attachChild(node);
    }
    
    public boolean isGrounded(){
        return grounded;
    }
    public ArrayList<Chunk> intersectingChunks(World which){
        ArrayList<Chunk> myChunks=new ArrayList<Chunk>();
        
        return myChunks;
    }
    public Vector2f getLocation(){
        return position.clone();
    }
    public Vector2f getBottomLeft(){
        return new Vector2f(position.x-length, position.y);
    }
    public Vector2f getTopRight(){
        return new Vector2f(position.x+length, position.y+height);
    }
    
    public void move(float x, float y){
        position.addLocal(x, y);
        node.move(x, y, 0);
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
