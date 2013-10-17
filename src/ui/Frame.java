package ui;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import java.util.ArrayList;
import tools.CG;

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
        head = new Button(node, new Vector2f(0, y*0.95f), x*1.95f, y*0.09f, z+0.01f);
        head.changeColor(ColorRGBA.Orange);
        geo = CG.createBox(node, new Vector3f(x, y, z), Vector3f.ZERO, ColorRGBA.Blue);
    }
}
