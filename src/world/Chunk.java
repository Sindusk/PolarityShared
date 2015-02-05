package world;

import world.blocks.Block;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector2f;
import com.jme3.network.serializing.Serializable;
import com.jme3.scene.Node;
import java.util.ArrayList;
import world.blocks.BlockData;
import netdata.ChunkData;
import tools.Util.Vector2i;
import world.blocks.ColorBlockData;
import world.blocks.IconBlockData;
import world.blocks.WallData;

/**
 *
 * @author SinisteRing
 */
@Serializable
public class Chunk {
    public static final int SIZE = 8;   // Amount of blocks per chunk
    protected ArrayList<ArrayList<Block>> blocks = new ArrayList();
    protected Node parent;
    protected Node node = new Node("Chunk");
    protected boolean loaded;
    protected Vector2i key;
    protected Vector2f loc;
    protected ColorRGBA color;  // Temporary, for testing/visualization.
    
    public Chunk(){}    // For serialization.
    public Chunk(Node parent, Vector2i key){
        this.parent = parent;
        this.key = key;
        this.loc = new Vector2f(key.x*SIZE, key.y*SIZE);
        this.color = new ColorRGBA(0, FastMath.nextRandomFloat(), FastMath.nextRandomFloat(), 1);
        parent.attachChild(node);
        loaded = true;
    }
    
    public boolean loaded(){
        return loaded;
    }
    public Vector2i getKey(){
        return key;
    }
    public Block getBlock(Vector2i coords){
        return blocks.get(coords.x).get(coords.y);
    }
    public ChunkData toData(){
        ArrayList<ArrayList<BlockData>> blockDatas = new ArrayList();
        int x = 0;
        int y;
        while(x < blocks.size()){
            y = 0;
            blockDatas.add(new ArrayList());
            while(y < blocks.get(x).size()){
                blockDatas.get(x).add(blocks.get(x).get(y).getData());
                y++;
            }
            x++;
        }
        return new ChunkData(key.x, key.y, blockDatas);
    }
    
    public void generateBlocks(ArrayList<ArrayList<BlockData>> blockDatas){
        int x = 0;
        int y;
        BlockData data;
        while(x < blockDatas.size()){
            y = 0;
            blocks.add(new ArrayList());
            while(y < blockDatas.size()){
                data = blockDatas.get(x).get(y);
                if(data instanceof ColorBlockData){
                    Block b = new Block(node, (ColorBlockData)data);
                    blocks.get(x).add(b);
                }else if(data instanceof IconBlockData){
                    Block b = new Block(node, (IconBlockData)data);
                    blocks.get(x).add(b);
                }
                y++;
            }
            x++;
        }
    }
    public void generateBlocks(){
        int x = 0;
        int y;
        while(x < Chunk.SIZE){
            y = 0;
            blocks.add(new ArrayList());
            while(y < Chunk.SIZE){
                float rng = FastMath.nextRandomInt(0, 50);
                if(rng <= 2){
                    blocks.get(x).add(new Block(node, new WallData(new Vector2f(loc.x+x, loc.y+y), ColorRGBA.Red)));
                }else if(rng <= 27){
                    blocks.get(x).add(new Block(node, new IconBlockData(new Vector2f(loc.x+x, loc.y+y), "grass")));
                }else{
                    blocks.get(x).add(new Block(node, new IconBlockData(new Vector2f(loc.x+x, loc.y+y), "wood")));
                }
                y++;
            }
            x++;
        }
    }
    public void load(){
        parent.attachChild(node);
        loaded = true;
    }
    public void unload(){
        node.removeFromParent();
        loaded = false;
    }
}
