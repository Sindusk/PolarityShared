package ui;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import input.Binding;
import java.util.ArrayList;
import tools.CG;
import tools.S;

/**
 * Frames are UI Elements which are able to be moved around the screen at will dynamically.
 * @author SinisteRing
 */
public class Frame extends UIElement {
    protected ArrayList<UIElement> controls = new ArrayList();
    protected Button head;
    
    public Frame(Node parent, Vector2f loc, float x, float y, float z){
        super(parent, loc, x, y, z);
        x /= 2.0f;
        y /= 2.0f;
        final Frame frame = this;
        head = new Button(node, new Vector2f(0, y*0.93f), x*1.95f, y*0.13f, z+5f){
            @Override
            public void onAction(Vector2f cursorLoc, String bind, boolean down, float tpf){
                if(bind.equals(Binding.LClick.toString())){
                    if(down){
                        S.getInputHandler().moving = frame;
                    }
                }
            }
        };
        head.changeColor(ColorRGBA.Orange);
        controls.add(head);
        geo = CG.createBox(node, new Vector3f(x, y, z), Vector3f.ZERO, ColorRGBA.Blue);
    }
    
    // Default override to ensure all inner controls are interacted with
    @Override
    public void onAction(Vector2f cursorLoc, String bind, boolean down, float tpf){
        UIElement e = checkControls(cursorLoc);
        if(e != null){
            e.onAction(cursorLoc, bind, down, tpf);
        }
    }
    
    // Getters
    public Button getHeader(){
        return head;
    }
    
    // Sets the text on the header (acts as a title)
    public void setTitle(String str){
        head.setText(str);
    }
    public void addControl(UIElement e){
        controls.add(e);
    }
    
    protected UIElement checkControls(Vector2f cursorLoc){
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
    
    // Move the frame a given amount
    public void move(float x, float y){
        node.move(x, y, 0);
        updateBounds(x, y);
        for(UIElement e : controls){
            e.updateBounds(x, y);
        }
    }
}
