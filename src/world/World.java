package world;

import action.ProjectileAttack;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Node;
import entity.Entity;
import entity.PlayerEntity;
import entity.Projectile;
import java.util.ArrayList;
import java.util.HashMap;
import tools.Sys;
import tools.Util.Vector2i;

/**
 *
 * @author SinisteRing
 */
public class World {
    protected HashMap<Vector2i, Chunk> data = new HashMap();
    protected Node node = new Node("World");
    protected ArrayList<Entity> entities=new ArrayList<Entity>();
    protected int seed;
    
    public World(int seed){
        this.seed = seed;   // Not yet used
    }
    
    // Getters
    public Node getNode(){
        return node;
    }
    
    public Projectile addProjectile(ProjectileAttack attack){
        Projectile p = new Projectile(node, attack);    // Creates the projectile class data
        p.create(attack.getStart());                    // Creates the projectile entity
        // tbi
        return p;
    }
    public PlayerEntity addPlayerEntity(ColorRGBA color){
        PlayerEntity e = new PlayerEntity(node, color);
        entities.add(e);    // Adds to the list of entities in the world
        return e;
    }
    
    public void update(float tpf){
        // Updates all entities currently in the world
        // Might need to be changed, sounds super inefficient...
        for(Entity t:entities){
            t.update(tpf);
        }
    }
    
    // World generation algorithm
    public void generate(){
        Sys.setWorld(this);
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
