package world;

import ai.pathfinding.Path;
import character.GameCharacter;
import character.Monster;
import world.blocks.Block;
import events.ProjectileEvent;
import character.Player;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.network.HostedConnection;
import com.jme3.scene.Node;
import entity.Entity;
import entity.LivingEntity;
import entity.MonsterEntity;
import entity.PlayerEntity;
import entity.Projectile;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.HashMap;
import netdata.ChunkData;
import netdata.requests.ChunkRequest;
import tools.GeoFactory;
import tools.Sys;
import tools.Vector2i;
import world.blocks.BlockData;

/**
 *
 * @author SinisteRing
 */
public class World {
    private static final int START_SIZE = 3;    //Excludes (0,0), chunks in each direction
    private static final int LOAD_DISTANCE = 6;
    
    protected Node pathNode = new Node();
    protected HashMap<Vector2i, Chunk> chunkMap = new HashMap();
    protected Node node = new Node("World");
    protected ArrayList<Entity> entities = new ArrayList<Entity>();
    protected QuadTree quadTree = new QuadTree(0, new Rectangle2D.Float(-100, -100, 200, 200));
    protected int seed;
    
    public World(int seed) {
        this.seed = seed;   // Not yet used
    }
    
    // Getters
    public Node getNode() {
        return node;
    }

    public ArrayList<Entity> getEntities() {
        return entities;
    }

    public Vector2i getChunkKey(Vector2f loc){
        return new Vector2i(Math.round(((loc.x + 0.5f) / Chunk.BLOCKS_PER_CHUNK) - 0.5f), Math.round(((loc.y + 0.5f) / Chunk.BLOCKS_PER_CHUNK) - 0.5f));
    }
    public Chunk getChunk(Vector2f loc) {
        return chunkMap.get(getChunkKey(loc));
    }
    public Chunk getChunk(Vector2i index){
        return chunkMap.get(index);
    }

    public Block getBlock(Vector2f loc) {
        Vector2i chunkKey = getChunkKey(loc);
        Vector2i coords = new Vector2i(Math.round(loc.x) - (chunkKey.x * Chunk.BLOCKS_PER_CHUNK), Math.round(loc.y) - (chunkKey.y * Chunk.BLOCKS_PER_CHUNK));
        Chunk c = getChunk(chunkKey);
        if (c != null) {
            return c.getBlock(coords);
        }
        return null;
    }
    public Block getBlock(Vector3f loc) {
        return getBlock(new Vector2f(loc.x, loc.y));
    }
    public Block getBlock(int x, int y) {
        return getBlock(new Vector2f(x, y));
    }
    
    // [Server-Side] Updates QuadTree bounds to match the ever-expanding world
    public void ensureQuadTree(Vector2i key){
        Rectangle2D.Float bounds = quadTree.getBounds();
        double minX = bounds.getMinX();
        double maxX = bounds.getMaxX();
        double minY = bounds.getMinY();
        double maxY = bounds.getMaxY();
        boolean update = false;
        if(key.x*Chunk.BLOCKS_PER_CHUNK < minX){
            minX = key.x*Chunk.BLOCKS_PER_CHUNK;
            update = true;
        }
        if(key.x*Chunk.BLOCKS_PER_CHUNK > maxX){
            maxX = key.x*Chunk.BLOCKS_PER_CHUNK;
            update = true;
        }
        if(key.y*Chunk.BLOCKS_PER_CHUNK < minY){
            minY = key.y*Chunk.BLOCKS_PER_CHUNK;
            update = true;
        }
        if(key.y*Chunk.BLOCKS_PER_CHUNK > maxY){
            maxY = key.y*Chunk.BLOCKS_PER_CHUNK;
            update = true;
        }
        if(update){
            quadTree.updateBounds(minX, maxX, minY, maxY);
        }
    }
    
    // [Server-Side] Gathers data from the start location to send to the player
    public void sendData(HostedConnection conn) {
        int x = 0 - START_SIZE;
        int y;
        while (x < START_SIZE) {
            y = 0 - START_SIZE;
            while (y < START_SIZE) {
                conn.send(chunkMap.get(new Vector2i(x, y)).toData());
                y++;
            }
            x++;
        }
    }

    public Projectile addProjectile(ProjectileEvent attack) {
        Projectile p = new Projectile(node, attack);        // Creates the projectile class data
        p.create(0.25f, attack.getStart(), attack.getTarget());    // Creates the projectile entity
        entities.add(p);    // Adds to the list of entities in the world
        return p;
    }
    public void destroyProjectile(int hashCode) {
        Projectile p;
        for (Entity e : entities) {
            if (e instanceof Projectile) {
                p = (Projectile) e;
                if (p.getEvent().getHashCode() == hashCode) {
                    p.destroy();
                    return;
                }
            }
        }
    }

    public PlayerEntity addPlayerEntity(Player player, ColorRGBA color) {
        PlayerEntity e = new PlayerEntity(node, player, color);
        entities.add(e);    // Adds to the list of entities in the world
        return e;
    }
    public MonsterEntity addMonsterEntity(Monster mob){
        MonsterEntity e = new MonsterEntity(node, mob);
        entities.add(e);
        return e;
    }
    
    // [Server-Side] Returns true if there is no walls intersecting the two given points
    // *** NOT YET IMPLEMENTED ***
    public boolean clearShot(Vector2f start, Vector2f target){
        return true;
    }
    // [Server-Side] Finds and returns the closest enemy of the passed in Entity owner
    public LivingEntity findClosestEnemy(Entity entity, float range){
        GameCharacter owner = entity.getOwner();
        Vector2f loc = entity.getLocation();
        ArrayList<Entity> nearbyEntities = new ArrayList();
        quadTree.retrieve(nearbyEntities, new Rectangle2D.Float(loc.x-range, loc.y-range, range*2, range*2));
        for(Entity e: nearbyEntities){
            if(e instanceof LivingEntity && owner.isEnemy(e.getOwner()) && e.getLocation().distance(loc) < range){
                return (LivingEntity) e;
            }
        }
        return null;
    }
    
    // [Mutual] Main world update loop
    public void update(float tpf) {
        // Updates all entities currently in the world
        // Might need to be changed, sounds super inefficient...
        quadTree.clear();
        int i = 0;
        Entity t;   // Temp entity
        while (i < entities.size()) {
            t = entities.get(i);
            if (entities.get(i).isDestroyed()) {
                entities.remove(entities.get(i));   // Remove any entities that are destroyed, before updating.
            } else {
                t.update(tpf);
                quadTree.insert(t);
                i++;
            }
        }
    }
    
    // [Server-Side] Collision detection loop
    public void serverUpdate(float tpf) {
        update(tpf);
        ArrayList<Entity> collisions = new ArrayList();
        // Do collision checking for all remaining entities
        int i = 0;
        Entity t;   // Temp entity
        while (i < entities.size()) {
            t = entities.get(i);
            t.checkCollisions(Sys.getNetwork(), quadTree.retrieve(collisions, t));
            i++;
        }
    }
    
    // A testing function that shows a line for a path, generated through AStar Pathfinding
    public void showPath(Path path) {
        pathNode.removeFromParent();
        pathNode = new Node();
        int i = 0;
        while (i < path.getLength() - 1) {
            Vector2i step1 = path.getStep(i);
            Vector2i step2 = path.getStep(i + 1);
            Vector2f loc1 = getBlock(step1.x, step1.y).getData().getLocation();
            Vector2f loc2 = getBlock(step2.x, step2.y).getData().getLocation();
            Vector3f geoLoc1 = new Vector3f(loc1.x, loc1.y, 2);
            Vector3f geoLoc2 = new Vector3f(loc2.x, loc2.y, 2);
            GeoFactory.createLine(pathNode, 5f, geoLoc1, geoLoc2, ColorRGBA.Blue);
            i++;
        }
        node.attachChild(pathNode);
    }
    
    // [Client-Side] Updates chunk from a ChunkData message
    public void updateChunk(ChunkData d) {
        Vector2i key = d.getKey();
        if(!chunkMap.containsKey(key) || chunkMap.get(key) == null){
            Chunk chunk = new Chunk(node, key);
            chunk.loadBlocks(d.getBlocks());
            chunkMap.put(key, chunk);
        }
    }
    
    // [Client-Side] Checks the chunks around a players position when it changes to load new chunks
    public void checkChunks(Player p) {
        Vector2f loc = p.getLocation();
        Vector2i chunkKey = getChunkKey(loc);
        if(p.getChunkKey() == null || !chunkKey.equals(p.getChunkKey())){
            int minX = chunkKey.x-LOAD_DISTANCE;
            int maxX = chunkKey.x+LOAD_DISTANCE;
            int minY = chunkKey.y-LOAD_DISTANCE;
            int maxY = chunkKey.y+LOAD_DISTANCE;
            // Reload the surrounding chunks
            int x = minX;
            while(x <= maxX){
                int y = minY;
                while(y <= maxY){
                    Vector2i newKey = new Vector2i(x, y);
                    if(!chunkMap.containsKey(newKey) || chunkMap.get(newKey) == null){
                        Sys.getNetwork().send(new ChunkRequest(newKey));
                    }
                    y++;
                }
                x++;
            }
            // Nullify any chunks out of bounds
            for(Vector2i key : chunkMap.keySet()){
                if(chunkMap.get(key) != null && (key.x < minX || key.x > maxX || key.y < minY || key.y > maxY)){
                    chunkMap.get(key).unload();
                    chunkMap.put(key, null);
                    //Sys.getNetwork().send(new ChunkUnload(key));
                }
            }
        }
    }
    
    // [Server-Side] Obtains blocks adjacent to the passed in location
    public ArrayList<BlockData> getAdjacentBlockDatas(Vector2f loc){
        ArrayList<BlockData> results = new ArrayList();
        Block test = getBlock(loc.add(new Vector2f(0, 1))); // North
        if(test != null){
            results.add(test.getData());
        }
        test = getBlock(loc.add(new Vector2f(1, 1)));   // NorthEast
        if(test != null){
            results.add(test.getData());
        }
        test = getBlock(loc.add(new Vector2f(1, 0)));   // East
        if(test != null){
            results.add(test.getData());
        }
        test = getBlock(loc.add(new Vector2f(1, -1)));  // SouthEast
        if(test != null){
            results.add(test.getData());
        }
        test = getBlock(loc.add(new Vector2f(0, -1)));  // South
        if(test != null){
            results.add(test.getData());
        }
        test = getBlock(loc.add(new Vector2f(-1, -1))); // SouthWest
        if(test != null){
            results.add(test.getData());
        }
        test = getBlock(loc.add(new Vector2f(-1, 0)));  // West
        if(test != null){
            results.add(test.getData());
        }
        test = getBlock(loc.add(new Vector2f(-1, 1)));  // NorthWest
        if(test != null){
            results.add(test.getData());
        }
        return results;
    }
    
    // [Server-Side] Obtains chunks adjacent to the passed in key
    private ArrayList<Chunk> getAdjacentChunks(Vector2i key){
        ArrayList<Chunk> results = new ArrayList();
        Vector2i newKey = new Vector2i(key.x, key.y+1); // North
        if(chunkMap.containsKey(newKey)){
            results.add(chunkMap.get(newKey));
        }
        newKey = new Vector2i(key.x+1, key.y);  // East
        if(chunkMap.containsKey(newKey)){
            results.add(chunkMap.get(newKey));
        }
        newKey = new Vector2i(key.x, key.y-1);  // South
        if(chunkMap.containsKey(newKey)){
            results.add(chunkMap.get(newKey));
        }
        newKey = new Vector2i(key.x-1, key.y);  // West
        if(chunkMap.containsKey(newKey)){
            results.add(chunkMap.get(newKey));
        }
        return results;
    }
    
    // [Server-Side] Loads a chunk, or generates it if it does not exist
    public ChunkData loadChunk(Vector2i key){
        // TODO: Use if/else to determine whether to load from file or generate new chunk
        ArrayList<Chunk> adjacentChunks = getAdjacentChunks(key);
        Chunk chunk = new Chunk(node, key);
        chunkMap.put(key, chunk);
        if(adjacentChunks.size() > 0){
            chunk.generateChunk(this, adjacentChunks);
        }else{
            chunk.generateRandom();
        }
        ensureQuadTree(key);
        return chunk.toData();
    }
    
    // [Server-Side] Requests a chunk be loaded, or generated if it does not exist
    public ChunkData requestChunk(ChunkRequest request){
        Vector2i key = request.getKey();
        if(chunkMap.containsKey(key) && chunkMap.get(key) != null){
            return chunkMap.get(key).toData();
        }else{
            return loadChunk(key);
        }
    }

    // [Server-Side] Generates the initial chunks for the starting zone of the world
    public void generateStart() {
        int x = -START_SIZE;
        while (x < START_SIZE) {
            int y = -START_SIZE;
            while (y < START_SIZE) {
                loadChunk(new Vector2i(x, y));
                y++;
            }
            x++;
        }
    }
}
