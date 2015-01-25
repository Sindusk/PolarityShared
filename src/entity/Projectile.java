package entity;

import action.ProjectileAttack;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import java.util.ArrayList;
import netdata.destroyers.DestroyProjectileData;
import tools.GeoFactory;
import tools.Sys;
import world.blocks.WallData;

/**
 * Projectile Class
 * @author Sindusk
 */
public class Projectile extends Entity{
    protected ProjectileAttack attack;
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
    public Projectile(Node parent, ProjectileAttack attack){
        super(parent);
        this.attack = attack;
    }
    
    public ProjectileAttack getAttack(){
        return attack;
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
        Vector2f moddedStart = start.add(direction);
        node.setLocalTranslation(new Vector3f(moddedStart.x, moddedStart.y, 0));
        geo = GeoFactory.createSphere(node, "projectile", radius, Vector3f.ZERO, ColorRGBA.Pink);
        oldLoc = moddedStart;
        newLoc = moddedStart;
    }
    
    @Override
    public void update(float tpf){
        super.update(tpf);
        moveLocation(direction.mult(tpf*attack.getSpeed()));
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
    public ArrayList<Entity> checkCollisions(ArrayList<Entity> collisions){
        collisions = super.checkCollisions(collisions);
        int i = 0;
        while(i < collisions.size()){
            // Filters: Owner of this projectile
            if(attack.getOwner().getEntity() == collisions.get(i)){
                collisions.remove(i);
            }else{
                i++;
            }
        }
        if(collisions.size() > 0){
            if(attack.getEvent().onCollide(collisions)){
                destroy();
                Sys.getNetwork().send(new DestroyProjectileData(this.hashCode()));
            }
        }
        return collisions;
    }
}
