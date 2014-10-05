package world;

import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector2f;
import com.jme3.scene.Node;
import java.util.ArrayList;
import tools.Util.Vector2i;

/**
 *
 * @author SinisteRing
 */
public class Chunk {
    public static final int SIZE = 8;   // Amount of blocks per chunk
    protected ArrayList<ArrayList<Block>> blocks = new ArrayList();
    protected Node node = new Node("Chunk");
    protected Vector2i key;
    protected Vector2f loc;
    protected ColorRGBA color;  // Temporary, for testing/visualization.
    
    public Chunk(Node parent, Vector2i key){
        this.key = key;
        this.loc = new Vector2f(key.x*SIZE, key.y*SIZE);
        this.color = new ColorRGBA(FastMath.nextRandomFloat(), FastMath.nextRandomFloat(), FastMath.nextRandomFloat(), 1);
        parent.attachChild(node);
    }
    
    public void generateBlocks(){
        int x = 0;
        int y;
        while(x < Chunk.SIZE){
            y = 0;
            blocks.add(new ArrayList());
            while(y < Chunk.SIZE){
                blocks.get(x).add(new Block(node, color, loc.x+x, loc.y+y));
                y++;
            }
            x++;
        }
    }
}
