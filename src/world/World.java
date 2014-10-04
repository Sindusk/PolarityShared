package world;

import action.ProjectileAttack;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Node;
import entity.Entity;
import entity.PlayerEntity;
import entity.Projectile;
import java.awt.Rectangle;
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
    protected QuadTree quadTree = new QuadTree(0, new Rectangle(-100, -100, 200, 200));
    protected int seed;
    
    public World(int seed){
        this.seed = seed;   // Not yet used
    }
    
    // Getters
    public Node getNode(){
        return node;
    }
    public ArrayList<Entity> getEntities(){
        return entities;
    }
    
    public Projectile addProjectile(ProjectileAttack attack){
        Projectile p = new Projectile(node, attack);        // Creates the projectile class data
        p.create(0.5f, attack.getStart(), attack.getTarget());    // Creates the projectile entity
        entities.add(p);
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
        quadTree.clear();
        int i = 0;
        Entity t;   // Temp entity
        while(i < entities.size()){
            t = entities.get(i);
            if(entities.get(i).isDestroyed()){
                entities.remove(entities.get(i));
            }else{
                t.update(tpf);
                quadTree.insert(t);
                i++;
            }
        }
        ArrayList collisions = new ArrayList();
        quadTree.retrieve(collisions, entities.get(0));
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
