package world.blocks;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.network.serializing.Serializable;

/**
 *
 * @author SinisteRing
 */
@Serializable
public class BlockData {
    protected Vector2f loc;
    protected ColorRGBA color;
    
    public BlockData(){}    // For serialization
    public BlockData(Vector2f loc, ColorRGBA color){
        this.loc = loc;
        this.color = color;
    }
    
    public Vector2f getLocation(){
        return loc;
    }
    public Vector3f get3DLocation(){
        return new Vector3f(loc.x, loc.y, 0);
    }
    public ColorRGBA getColor(){
        return color;
    }
}
