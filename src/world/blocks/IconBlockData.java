package world.blocks;

import com.jme3.math.Vector2f;
import com.jme3.network.serializing.Serializable;

/**
 *
 * @author SinisteRing
 */
@Serializable
public class IconBlockData extends BlockData {
    protected String icon;
    
    public IconBlockData(){}    // For serialization
    public IconBlockData(Vector2f loc, String icon){
        super(loc);
        this.icon = icon;
    }
    
    public String getIcon(){
        return icon;
    }
}
