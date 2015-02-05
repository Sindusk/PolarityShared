package world.blocks;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.network.serializing.Serializable;

/**
 *
 * @author SinisteRing
 */
@Serializable
public class ColorBlockData extends BlockData {
    protected ColorRGBA color;
    
    public ColorBlockData(){}
    public ColorBlockData(Vector2f loc, ColorRGBA color){
        super(loc);
        this.color = color;
    }
    
    public ColorRGBA getColor(){
        return color;
    }
}
