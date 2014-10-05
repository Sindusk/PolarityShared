package items;

import com.jme3.network.serializing.Serializable;
import types.AttackType;

/**
 *
 * @author SinisteRing
 */
@Serializable
public class Weapon extends Item {
    protected AttackType attackType;
    protected float speed = 8f;
    
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
    public float getSpeed(){
        return speed;
    }
}
