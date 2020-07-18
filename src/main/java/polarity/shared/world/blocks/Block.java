package polarity.shared.world.blocks;

import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import polarity.shared.tools.GeoFactory;
import polarity.shared.tools.Util;

/**
 *
 * @author SinisteRing
 */
public class Block {
    protected BlockData data;
    protected Geometry geo;
    
    public Block(Node parent, BlockData data){
        this.data = data;
        geo = GeoFactory.createBox(parent, new Vector3f(0.5f, 0.5f, 0f), data.get3DLocation(), Util.getBlockIcon(data.getIcon()));
        // Quads are not working very well at the moment:
        //geo = GeoFactory.createQuad(parent, "block", 0.9f, 0.9f, data.get3DLocation(), Util.getBlockIcon(data.getIcon()));
    }
    
    public BlockData getData(){
        return data;
    }
}
