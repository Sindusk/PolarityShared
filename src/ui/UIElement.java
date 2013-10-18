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
        Vector3f offset = parent.getLocalTranslation();
        bounds = new Vector4f(offset.x+center.x-x, offset.x+center.x+x, offset.y+center.y-y, offset.y+center.y+y);
        priority = offset.z+z;
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
    
    // Update the bounds (when UI elements move)
    public void updateBounds(float x, float y){
        //T.log(bounds.toString());
        bounds = new Vector4f(bounds.x+x, bounds.y+x, bounds.z+y, bounds.w+y);
        //T.log(bounds.toString());
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
