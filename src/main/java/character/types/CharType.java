package character.types;

import com.jme3.network.serializing.Serializable;

/**
 *
 * @author SinisteRing
 */
@Serializable
public enum CharType {
    UNKNOWN,    // Entity defualt, not overridden in a subclass
    LIVING,     // LivingEntity default, not overriden in a subclass
    
    // Living Entities
    PLAYER, MONSTER,
    
    // Nonliving Entities
    PROJECTILE;
}
