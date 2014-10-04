/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import action.ProjectileAttack;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import tools.GeoFactory;

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
    
    /**
     * Creates the geometry of the projectile.
     * <p>
     * This is required before updating the projectile, and is recommended to be done immediately after Projectile instanciation.
     * @param radius Radius for the sphere that the projectile model will have.
     * @param start Start location of the projectile.
     * @param target Target location of the projectile. In general used to simply figure out direction the projectile travels.
     */
    public void create(float radius, Vector2f start, Vector2f target){
        geo = GeoFactory.createSphere(node, "projectile", radius, Vector3f.ZERO, ColorRGBA.Pink);
        oldLoc = start;
        newLoc = start;
        direction = target.subtract(start).normalize();
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
}
