package world;

import world.blocks.Block;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector2f;
import com.jme3.network.serializing.Serializable;
import com.jme3.scene.Node;
import java.util.ArrayList;
import java.util.HashMap;
import world.blocks.BlockData;
import netdata.ChunkData;
import tools.Vector2i;
import world.blocks.BlockType;
import world.blocks.TerrainData;
import world.creation.BlockFactory;

/**
 *
 * @author SinisteRing
 */
@Serializable
public class Chunk {
    public static final int BLOCKS_PER_CHUNK = 5;   // Amount of blocks per chunk
    public static final int CENTER_CHUNK = (BLOCKS_PER_CHUNK-1)/2;  // Center of the chunk [Requires odd number]
    
    protected HashMap<Vector2i,Block> blocks = new HashMap();
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
        this.loc = new Vector2f(key.x*BLOCKS_PER_CHUNK, key.y*BLOCKS_PER_CHUNK);
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
        return blocks.get(coords);
    }
    public ChunkData toData(){
        ArrayList<ArrayList<BlockData>> blockDatas = new ArrayList();
        int x = 0;
        int y;
        while(x < BLOCKS_PER_CHUNK){
            y = 0;
            blockDatas.add(new ArrayList());
            while(y < BLOCKS_PER_CHUNK){
                blockDatas.get(x).add(blocks.get(new Vector2i(x, y)).getData());
                y++;
            }
            x++;
        }
        return new ChunkData(key.x, key.y, blockDatas);
    }
    
    // [Client-Side] Loads the blocks from an ArrayList of pre-determined blocks
    public void loadBlocks(ArrayList<ArrayList<BlockData>> blockDatas){
        int x = 0;
        int y;
        BlockData data;
        while(x < blockDatas.size()){
            y = 0;
            while(y < blockDatas.size()){
                data = blockDatas.get(x).get(y);
                Block b = new Block(node, data);
                blocks.put(new Vector2i(x, y), b);
                y++;
            }
            x++;
        }
    }
    
    // [Server-Side] Generates a block, then attempts to generate all adjacent
    public void recursiveBlockGen(World world, Vector2i start){
        if(start.x < 0 || start.x >= BLOCKS_PER_CHUNK || start.y < 0 || start.y >= BLOCKS_PER_CHUNK || blocks.containsKey(start)){
            // Out of bounds or already has block here
            return;
        }
        Vector2f blockLoc = new Vector2f(loc.x+start.x, loc.y+start.y);
        ArrayList<BlockData> adjacentBlocks = world.getAdjacentBlockDatas(blockLoc);
        blocks.put(start, new Block(node, BlockFactory.generateBlock(new Vector2f(loc.x+start.x, loc.y+start.y), adjacentBlocks)));
        
        // Long way of going through, but it works
        recursiveBlockGen(world, start.add(0, 1));  // North
        recursiveBlockGen(world, start.add(1, 0));  // East
        recursiveBlockGen(world, start.add(0, -1)); // South
        recursiveBlockGen(world, start.add(-1, 0)); // West
    }
    
    // [Server-Side] Generates a chunk, starting as close to nearby generated chunks as possible
    public void generateChunk(World world, ArrayList<Chunk> adjacentChunks){
        Vector2i start = new Vector2i(CENTER_CHUNK, CENTER_CHUNK);
        for(Chunk chunk : adjacentChunks){
            int modX = (chunk.getKey().x-key.x)*CENTER_CHUNK;
            int modY = (chunk.getKey().y-key.y)*CENTER_CHUNK;
            start.addLocal(new Vector2i(modX, modY));
        }
        recursiveBlockGen(world, start);
    }
    
    // [Server-Side] Generates a completely random chunk. Only used when no adjacent chunks are found.
    public void generateRandom(){
        int x = 0;
        int y;
        while(x < Chunk.BLOCKS_PER_CHUNK){
            y = 0;
            while(y < Chunk.BLOCKS_PER_CHUNK){
                blocks.put(new Vector2i(x, y), new Block(node, new TerrainData(new Vector2f(loc.x+x, loc.y+y), BlockType.GRAVEL)));
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
