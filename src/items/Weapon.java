package items;

import com.jme3.network.serializing.Serializable;
import types.AttackType;

/**
 *
 * @author SinisteRing
 */
@Serializable
public class Weapon extends Item {
    AttackType attackType;
    
    public Weapon(){
        this.attackType = AttackType.Normal;
    }
    
    public Weapon(String icon, AttackType attackType){
        super(icon);
        this.attackType = attackType;
    }
    
    public AttackType getAttackType(){
        return attackType;
    }
}
