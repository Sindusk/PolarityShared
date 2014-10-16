package world.blocks;

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
public class Block {
    protected Vector2f loc;
    protected Geometry geo;
    
    public Block(Node parent, ColorRGBA color, float x, float y){
        loc = new Vector2f(x, y);
        GeoFactory.createBox(parent, "block", new Vector3f(0.45f, 0.45f, 0f), new Vector3f(x, y, 0), color);
    }
}
