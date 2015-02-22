package entity;

import events.ProjectileEvent;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import character.types.CharType;
import events.Action;
import java.util.ArrayList;
import netdata.destroyers.DestroyProjectileData;
import network.GameNetwork;
import tools.GeoFactory;
import tools.Sys;
import world.blocks.WallData;

/**
 * Projectile Class
 * @author Sindusk
 */
public class Projectile extends Entity{
    protected ProjectileEvent event;
    protected Geometry geo;
    protected Vector2f direction;       // Normalized vector for directional movement.
    protected float lifetime = 5.0f;    // How long (in seconds) the projectile is to last.
    
    /**
     * Initializes the projectile.
     * <p>
     * Note: This does not actually create the Projectile geometry, nor identify it in the world space.
     * That operation is done in the create() method.
     * @param parent Parent node to attach the projectile to.
     * @param attack Data for the projectile, including the method used when the projectile encounters an object.
     */
    public Projectile(Node parent, ProjectileEvent event){
        super(parent, event.getOwner());
        this.event = event;
        this.type = CharType.PROJECTILE;
    }
    
    public ProjectileEvent getEvent(){
        return event;
    }
    
    /**
     * Creates the geometry of the projectile.
     * <p>
     * This is required before updating the projectile, and is recommended to be done immediately after Projectile instanciation.
     * @param radius Radius for the sphere that the projectile model will have.
     * @param start Start location of the projectile.
     * @param target Target location of the projectile. In general used to simply figure out direction the projectile travels.
     */
    public void create(float radius, Vector2f start, Vector2f target){
        direction = target.subtract(start).normalize();
        // Apply a push forward so the projectile spawns infront of the player, instead of on top
        Vector2f moddedStart = start.add(direction.mult(0.5f));
        node.setLocalTranslation(new Vector3f(moddedStart.x, moddedStart.y, 0));
        geo = GeoFactory.createSphere(node, "projectile", radius, Vector3f.ZERO, ColorRGBA.Pink);
        oldLoc = moddedStart;
        newLoc = moddedStart;
    }
    
    @Override
    public void update(float tpf){
        super.update(tpf);
        moveLocation(direction.mult(tpf*event.getSpeed()));
        lifetime -= tpf;
        if(lifetime <= 0){
            destroy();
        }
    }
    
    @Override
    public void moveLocation(Vector2f offset){
        Vector2f temp = newLoc.add(offset);
        // Destroy the projectile if it collides with a wall.
        if(Sys.getWorld().getBlock(temp) == null){
            return; // This ensures no errors occur if the client has not recieved the data for this area yet.
        }
        if(Sys.getWorld().getBlock(temp).getData() instanceof WallData){
            destroy();
            return;
        }
        super.moveLocation(offset);
    }
    
    @Override
    public ArrayList<Entity> checkCollisions(GameNetwork server, ArrayList<Entity> collisions){
        collisions = super.checkCollisions(server, collisions);
        int i = 0;
        while(i < collisions.size()){
            // Filters: Owner of this projectile, //or not an enemy of the owner
            if(owner.getEntity() == collisions.get(i) ){// || !owner.isEnemy(collisions.get(i).getOwner())){
                collisions.remove(i);
            }else{
                i++;
            }
        }
        if(collisions.size() > 0){
            i = 0;
            Entity t;   // Temp entity
            while(i < collisions.size()){
                t = collisions.get(i);
                if(t instanceof LivingEntity){
                    LivingEntity livingEntity = (LivingEntity) t; // Cast the Entity to a PlayerEntity to open up specific methods
                    for(Action action : event.getActions()){
                        action.onCollide(livingEntity);
                    }
                    destroy();
                    server.send(new DestroyProjectileData(this.hashCode()));
                }
                i++;
            }
        }
        return collisions;
    }
}
