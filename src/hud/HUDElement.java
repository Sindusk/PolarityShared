package hud;

import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import tools.Util;

/**
 *
 * @author Sindusk
 */
public class HUDElement {
    protected Node node = new Node("HUDElement Node");
    protected Vector2f loc;
    protected float priority;
    
    public HUDElement(Node parent, Vector2f loc){
        this.loc = loc;
        node.setLocalTranslation(loc.x, loc.y, 0);
        parent.attachChild(node);
    }
    
    public Vector3f getLocalTranslation(){
        return node.getLocalTranslation();
    }
    public void setPriority(float priority){
        node.setLocalTranslation(new Vector3f(loc.x, loc.y, priority));
        this.priority = priority;
    }
    
    public void update(float tpf){
        Util.log("[HUDElement] Error: Class does not have an update override", 1);
    }
}
