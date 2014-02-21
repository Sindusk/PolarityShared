package entity;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import tools.GeoFactory;

/**
 *
 * @author SinisteRing
 */
public class PlayerEntity extends Entity {
    protected Geometry geo;
    protected Geometry point;
    
    public PlayerEntity(Node parent, ColorRGBA color){
        super(parent);
        geo = GeoFactory.createBox(node, "PlayerEntity", new Vector3f(0.7f, 0.5f, 1), Vector3f.ZERO, color);
        point = GeoFactory.createBox(node, "PlayerEntity", new Vector3f(0.5f, 0.3f, 0.7f), new Vector3f(1f, 0f, 0), ColorRGBA.Red);
    }
}
