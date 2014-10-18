package hud;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import tools.GeoFactory;

/**
 *
 * @author SinisteRing
 */
public class DynamicBar extends HUDElement {
    protected Geometry fill;
    
    public DynamicBar(Node parent, Vector2f location, Vector2f size, ColorRGBA color){
        super(parent, location);
        fill = GeoFactory.createBox(node, new Vector3f(size.x*0.5f, size.y*0.5f, 0), Vector3f.ZERO, color);
    }
}
