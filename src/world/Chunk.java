/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package world;

import com.jme3.math.Vector2f;
import com.jme3.scene.Node;
import java.util.ArrayList;
import tools.T.Vector2i;

/**
 *
 * @author SinisteRing
 */
public class Chunk {
    private static final int SIZE = 32;
    protected ArrayList<ArrayList<Block>> blocks = new ArrayList();
    protected Node node = new Node("Chunk");
    protected Vector2i key;
    protected Vector2f loc;
    
    public Chunk(Node parent, Vector2i key){
        this.key = key;
        this.loc = new Vector2f(key.x*SIZE, key.y*SIZE);
        parent.attachChild(node);
    }
    
    public void generateBlocks(){
        int x = 0;
        int y;
        while(x < Chunk.SIZE){
            y = 0;
            blocks.add(new ArrayList());
            while(y < Chunk.SIZE){
                blocks.get(x).add(new Block(node, loc.x+x, loc.y+y));
                y++;
            }
            x++;
        }
    }
}
