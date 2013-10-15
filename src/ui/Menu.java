package ui;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import tools.CG;

/**
 *
 * @author SinisteRing
 */
public class Menu extends UIElement {
    public Menu(Node parent, Vector2f loc, float x, float y, float z){
        super(parent, loc.x, loc.x+x, loc.y, loc.y+y, z);
        x /= 2.0f;
        y /= 2.0f;
        geo = CG.createBox(node, "test", new Vector3f(x, y, 0), new Vector3f(loc.x+x, loc.y+y, z), ColorRGBA.Blue);
    }
}
