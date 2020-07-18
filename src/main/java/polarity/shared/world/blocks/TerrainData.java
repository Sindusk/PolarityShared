package polarity.shared.world.blocks;

import com.jme3.math.Vector2f;
import com.jme3.network.serializing.Serializable;

/**
 *
 * @author SinisteRing
 */
@Serializable
public class TerrainData extends BlockData {
    public TerrainData(){}    // For serialization
    public TerrainData(Vector2f loc, BlockType type){
        super(loc, type);
    }
}
