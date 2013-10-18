package ui;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import input.Binding;
import java.util.ArrayList;
import tools.CG;
import tools.S;
import tools.T;

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
        head = new Button(node, new Vector2f(0, y*0.95f), x*1.95f, y*0.09f, z+5f){
            @Override
            public void onAction(String bind, boolean down, float tpf){
                if(bind.equals(Binding.LClick.toString())){
                    if(down){
                        S.getInputHandler().moving = frame;
                    }else{
                        S.getInputHandler().moving = null;
                    }
                }
            }
        };
        head.changeColor(ColorRGBA.Orange);
        geo = CG.createBox(node, new Vector3f(x, y, z), Vector3f.ZERO, ColorRGBA.Blue);
    }
    
    // Getters
    public Button getHeader(){
        return head;
    }
    
    // Move the frame a given amount
    public void move(float distance, boolean up){
        if(up){
            node.move(0, distance, 0);
            head.updateBounds(0, distance);
            updateBounds(0, distance);
        }else{
            node.move(distance, 0, 0);
            head.updateBounds(distance, 0);
            updateBounds(distance, 0);
        }
    }
}
