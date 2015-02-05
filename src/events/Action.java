package events;

import com.jme3.network.serializing.Serializable;
import entity.Entity;
import entity.LivingEntity;
import java.util.ArrayList;

/**
 *
 * @author SinisteRing
 */
@Serializable
public class Action {
    public Action(){}    // For serialization
    
    /**
     * Method to override for collision when making a ProjectileAttack.
     * @param collisions ArrayList containing the entities filtered for collision.
     * @return Return true if you want the projectile to be destroyed, or false if you do not.
     */
    public boolean onCollide(LivingEntity entity){
        return false;
    }
}
