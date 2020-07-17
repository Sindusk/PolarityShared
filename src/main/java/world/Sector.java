package world;

import world.interfaces.TileBasedMap;
import com.jme3.math.Vector2f;
import entity.Entity;
import java.util.ArrayList;
import tools.Util;
import tools.Vector2i;

/**
 * Designates a section of the map of arbitrary size. Used for pathfinding.
 * @author Sindusk
 */
public class Sector implements TileBasedMap {
    public static final int PADDING = 1;
    
    protected ArrayList<ArrayList<Chunk>> chunks = new ArrayList();
    protected World world;
    protected int xOffset;
    protected int yOffset;
    
    public Sector(){}
    public Sector(World world, Vector2f loc1, Vector2f loc2){
        if(world.getChunk(loc1) == null || world.getChunk(loc2) == null){
            Util.log("[Sector] <Constructor> Critical Error: Sector is trying to use iwnvalid chunks!");
            return;
        }
        Vector2i index1 = world.getChunk(loc1).getKey();
        Vector2i index2 = world.getChunk(loc2).getKey();
        int minx = Math.min(index1.x, index2.x)-PADDING;
        int maxx = Math.max(index1.x, index2.x)+PADDING;
        int miny = Math.min(index1.y, index2.y)-PADDING;
        int maxy = Math.max(index1.y, index2.y)+PADDING;
        
        this.world = world;
        this.xOffset = minx*Chunk.BLOCKS_PER_CHUNK;
        this.yOffset = miny*Chunk.BLOCKS_PER_CHUNK;
        
        int x = minx;
        while(x < maxx+1){
            int y = miny;
            ArrayList<Chunk> list = new ArrayList();
            while(y < maxy+1){
                Chunk c = world.getChunk(new Vector2i(x, y));
                if(c != null){
                    list.add(world.getChunk(new Vector2i(x, y)));
                }
                y++;
            }
            chunks.add(list);
            x++;
        }
    }
    
    public int getXOffset(){
        return xOffset;
    }
    public int getYOffset(){
        return yOffset;
    }
    
    public int getWidthInTiles() {
        return chunks.size() * Chunk.BLOCKS_PER_CHUNK;
    }

    public int getHeightInTiles() {
        return chunks.get(0).size() * Chunk.BLOCKS_PER_CHUNK;
    }

    public void pathFinderVisited(int x, int y) {
        // Used for debugging
    }

    public boolean blocked(Entity entity, int x, int y) {
        if (!entity.canMove(world.getBlock(x+xOffset, y+yOffset))) {
            return true;
        }
        return false;
    }

    public float getCost(Entity entity, int sx, int sy, int tx, int ty) {
        return 1;   // Should return the movement speed modifier of each tile
    }
    
    @Override
    public String toString(){
        String s = "";
        for(ArrayList<Chunk> list : chunks){
            for(Chunk c : list){
                s += c.getKey().toString();
            }
            s += '\n';
        }
        return s;
    }
}
