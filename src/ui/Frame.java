package ui;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.scene.Node;
import input.Bind;
import tools.Sys;

/**
 * Frames are UI Elements which are able to be moved around the screen at will dynamically.
 * @author SinisteRing
 */
public class Frame extends Panel {
    protected Button head;
    
    public Frame(Node parent, Vector2f loc, float x, float y, float z){
        super(parent, loc, x, y, z);
        x /= 2.0f;
        y /= 2.0f;
        final Frame frame = this;
        head = new Button(node, new Vector2f(0, y*0.93f), x*1.95f, y*0.13f, z+5f){
            @Override
            public void onAction(Vector2f cursorLoc, String bind, boolean down, float tpf){
                if(bind.equals(Bind.LClick.toString())){
                    // CURRENTLY DEFUNCT
                }
            }
        };
        head.setColor(ColorRGBA.Orange);
        controls.add(head);
    }
    
    // Getters
    public Button getHeader(){
        return head;
    }
    
    // Sets the text on the header (acts as a title)
    public void setTitle(String str){
        head.setText(str);
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
