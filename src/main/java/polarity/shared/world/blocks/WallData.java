package polarity.shared.world.blocks;

import com.jme3.math.Vector2f;
import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Sindusk
 */
@Serializable
public class WallData extends BlockData {
    public WallData(){} // For serialization
    public WallData(Vector2f loc, BlockType type){
        super(loc, type);
    }
}
