package ui;

import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.math.Vector4f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import tools.T;

/**
 *
 * @author SinisteRing
 */
public class UIElement {
    protected Node node = new Node("UIElement");
    protected Geometry geo;
    protected Vector4f bounds;
    protected float priority;
    
    public UIElement(Node parent, Vector2f center, float x, float y, float z){
        x /= 2.0f;
        y /= 2.0f;
        node.setLocalTranslation(new Vector3f(center.x, center.y, z));
        bounds = new Vector4f(center.x-x, center.x+x, center.y-y, center.y+y);
        priority = z;
        parent.attachChild(node);
    }
    
    // Getters
    public float getPriority(){
        return priority;
    }
    
    // Action definition placeholder
    public void onAction(String bind, boolean down, float tpf){
        T.log("Error 3: No override for onAction in class "+this.getClass().toString());
    }
    
    // Check if the location given is within the bounds of the current UI element.
    public boolean withinBounds(Vector2f loc){
        if(loc.x >= bounds.x && loc.x <= bounds.y){
            if(loc.y >= bounds.z && loc.y <= bounds.w){
                return true;
            }
        }
        return false;
    }
}
