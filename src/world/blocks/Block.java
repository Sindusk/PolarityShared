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
    protected BlockData data;
    protected Geometry geo;
    
    public Block(Node parent, ColorRGBA color, float x, float y){
        data = new BlockData(new Vector2f(x, y), color);
        geo = GeoFactory.createBox(parent, "block", new Vector3f(0.45f, 0.45f, 0f), new Vector3f(x, y, 0), color);
    }
    public Block(Node parent, BlockData data){
        this.data = data;
        geo = GeoFactory.createBox(parent, "block", new Vector3f(0.45f, 0.45f, 0f), data.get3DLocation(), data.getColor());
    }
    
    public BlockData getData(){
        return data;
    }
}
