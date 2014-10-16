package world;

import world.blocks.Block;
import action.ProjectileAttack;
import character.Player;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import entity.Entity;
import entity.PlayerEntity;
import entity.Projectile;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.HashMap;
import tools.Sys;
import tools.Util;
import tools.Util.Vector2i;

/**
 *
 * @author SinisteRing
 */
public class World {
    private static final int SIZE_X = 10;
    private static final int SIZE_Y = 10;
    
    protected HashMap<Vector2i, Chunk> chunkMap = new HashMap();
    protected Node node = new Node("World");
    protected ArrayList<Entity> entities=new ArrayList<Entity>();
    protected QuadTree quadTree = new QuadTree(0, new Rectangle2D.Float(-100, -100, 200, 200));
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
    public Block getBlock(Vector2f loc){
        Vector2i chunk = new Vector2i(Math.round(((loc.x+0.5f)/Chunk.SIZE)-0.5f), Math.round(((loc.y+0.5f)/Chunk.SIZE)-0.5f));
        Vector2i coords = new Vector2i(Math.round(loc.x)-(chunk.x*Chunk.SIZE), Math.round(loc.y)-(chunk.y*Chunk.SIZE));
        Util.log("chunk = "+chunk);
        Chunk c = chunkMap.get(chunk);
        if(c == null){
            Util.log("Yup, it's null.");
        }
        return c.getBlock(coords);
    }
    public Block getBlock(Vector3f loc){
        return getBlock(new Vector2f(loc.x, loc.y));
    }
    
    public Projectile addProjectile(ProjectileAttack attack){
        Projectile p = new Projectile(node, attack);        // Creates the projectile class data
        p.create(0.4f, attack.getStart(), attack.getTarget());    // Creates the projectile entity
        entities.add(p);
        // tbi
        return p;
    }
    public PlayerEntity addPlayerEntity(Player player, ColorRGBA color){
        PlayerEntity e = new PlayerEntity(node, player, color);
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
                entities.remove(entities.get(i));   // Remove any entities that are destroyed, before updating.
            }else{
                t.update(tpf);
                quadTree.insert(t);
                i++;
            }
        }
        ArrayList<Entity> collisions = new ArrayList();
        // Do collision checking for all remaining entities
        i = 0;
        while(i < entities.size()){
            t = entities.get(i);
            t.checkCollisions(quadTree.retrieve(collisions, t));
            i++;
        }
        quadTree.retrieve(collisions, entities.get(0));
    }
    
    // World generation algorithm
    public void generate(){
        Sys.setWorld(this);
        Vector2i key;
        int x = 0-(SIZE_X/2);
        int y;
        while(x < SIZE_X/2){
            y = 0-(SIZE_Y/2);
            while(y < SIZE_Y/2){
                key = new Vector2i(x, y);
                Chunk chunk = new Chunk(node, key);
                chunk.generateBlocks();
                chunkMap.put(key, chunk);
                y++;
            }
            x++;
        }
    }
}
