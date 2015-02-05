package world.blocks;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Sindusk
 */
@Serializable
public class WallData extends ColorBlockData {
    public WallData(){} // For serialization
    public WallData(Vector2f loc, ColorRGBA color){
        super(loc, color);
    }
}
