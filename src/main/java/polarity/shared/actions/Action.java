package polarity.shared.actions;

import polarity.shared.character.GameCharacter;
import com.jme3.network.serializing.Serializable;
import polarity.shared.entity.LivingEntity;

/**
 * Actions are created when any ability is used.
 * These will generally lead into an EventChain, determined by the override for the action.
 * @author Sindusk
 */
@Serializable
public class Action {
    public Action(){}    // For serialization
    
    /**
     * Method to override for collision when making a ProjectileAttack.
     * @return Return true if you want the projectile to be destroyed, or false if you do not.
     */
    public boolean onCollide(GameCharacter owner, LivingEntity entity){
        return false;
    }
}
