package polarity.shared.world.blocks;

import com.jme3.export.InputCapsule;
import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.export.OutputCapsule;
import com.jme3.export.Savable;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.network.serializing.Serializable;
import java.io.IOException;
import polarity.shared.tools.Vector2i;
import polarity.shared.world.Chunk;

/**
 *
 * @author SinisteRing
 */
@Serializable
public class BlockData implements Savable {
    protected BlockType type;
    protected Vector2f loc = new Vector2f(0,0);
    
    public BlockData(){}    // For serialization
    public BlockData(Vector2f loc, BlockType type){
        this.loc = loc;
        this.type = type;
    }
    
    public BlockType getType(){
        return type;
    }
    public String getIcon(){
        return type.getIcon();
    }
    public Vector2f getLocation(){
        return loc;
    }
    public Vector3f get3DLocation(){
        return new Vector3f(loc.x, loc.y, 0);
    }
    
    public void updateLocation(Vector2i chunkKey, Vector2i blockKey){
        float x = (chunkKey.x*Chunk.BLOCKS_PER_CHUNK)+blockKey.x;
        float y = (chunkKey.y*Chunk.BLOCKS_PER_CHUNK)+blockKey.y;
        this.loc = new Vector2f(x, y);
    }
    
    public void write(JmeExporter ex) throws IOException {
        OutputCapsule capsule = ex.getCapsule(this);
        capsule.write(type, "type", null);
        capsule.write(loc, "loc", null);
    }
    
    public void read(JmeImporter im) throws IOException {
        InputCapsule capsule = im.getCapsule(this);
        type = capsule.readEnum("type", BlockType.class, BlockType.CONCRETE);
        loc = (Vector2f) capsule.readSavable("loc", new Vector2f(0,0));
    }
    
    @Override
    public String toString(){
        return type+" ["+loc.x+","+loc.y+"]";
    }
}
