package ui;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import tools.GeoFactory;

/**
 *
 * @author SinisteRing
 */
public class Menu extends UIElement {
    public Menu(Node parent, Vector2f loc, float x, float y, float z){
        super(parent, loc, x, y, z);
        x /= 2.0f;
        y /= 2.0f;
        geo = GeoFactory.createBox(node, "test", new Vector3f(x, y, 0), new Vector3f(0, 0, 0), ColorRGBA.Blue);
    }
}
