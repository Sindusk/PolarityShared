package entity;

import com.jme3.math.Vector3f;
import com.jme3.scene.Node;

/**
 * Entity - Represents any non-block object that is attached to the game world.
 * @author SinisteRing
 */
public abstract class Entity {
    protected Node node = new Node("Entity");
    
    public Entity(Node parent){
        parent.attachChild(node);
    }
    
    public Vector3f getLocation(){
        return node.getLocalTranslation();
    }
    
    public void move(float x, float y, float z){
        node.move(x, y, z);
    }
    public void move(Vector3f vector){
        node.move(vector);
    }
}
