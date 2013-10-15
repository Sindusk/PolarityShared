/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package world;

import com.jme3.scene.Node;
import java.util.HashMap;
import tools.T.Vector2i;

/**
 *
 * @author SinisteRing
 */
public class World {
    protected HashMap<Vector2i, Chunk> data = new HashMap();
    protected Node node = new Node("World");
    protected int seed;
    
    public World(int seed){
        this.seed = seed;
    }
    
    // Getters
    public Node getNode(){
        return node;
    }
    
    // World generation algorithm
    public void generate(){
        Vector2i key = new Vector2i(0, -1);
        Chunk chunk = new Chunk(node, key);
        chunk.generateBlocks();
        data.put(key, chunk);
        key = new Vector2i(-1, -1);
        chunk = new Chunk(node, key);
        chunk.generateBlocks();
        data.put(key, chunk);
    }
}
