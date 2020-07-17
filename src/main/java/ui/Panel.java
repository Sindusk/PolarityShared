package ui;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import java.util.ArrayList;
import tools.GeoFactory;

/**
 *
 * @author SinisteRing
 */
public class Panel extends UIElement {
    protected ArrayList<UIElement> controls = new ArrayList();
    
    public Panel(Node parent, Vector2f loc, float x, float y, float z){
        super(parent, loc, x, y, z);
        x /= 2.0f;
        y /= 2.0f;
        geo = GeoFactory.createBox(node, new Vector3f(x, y, 0), Vector3f.ZERO, ColorRGBA.Blue);
    }
    
    // Default override to ensure all inner controls are interacted with
    @Override
    public void onAction(Vector2f cursorLoc, String bind, boolean down, float tpf){
        UIElement e = checkControls(cursorLoc);
        if(e != null){
            e.onAction(cursorLoc, bind, down, tpf);
        }
    }
    
    public void addControl(UIElement e){
        controls.add(e);
    }
    
    public UIElement checkControls(Vector2f cursorLoc){
        // Find all UI elements that are underneath the cursor location
        ArrayList<UIElement> results = new ArrayList();
        for(UIElement e : controls){
            if(e.withinBounds(cursorLoc)){
                results.add(e);
            }
        }
        // Parse the results and take an action according to how many there are
        if(results.size() > 0){
            // If one result, use it
            if(results.size() == 1){
                return results.get(0);
            }else{ // Otherwise, iterate through and find the one on top
                int i = 1;
                int result = 0;
                float current = results.get(0).getPriority();
                while(i < results.size()){
                    if(current < results.get(i).getPriority()){
                        result = i;
                    }
                    i++;
                }
                return results.get(result);
            }
        }
        return null;
    }
}
