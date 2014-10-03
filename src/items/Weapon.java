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
    protected float speed;
    
    public Weapon(){
        this.attackType = AttackType.Normal;
        this.speed = 0.5f;
    }
    
    public Weapon(String icon, AttackType attackType){
        super(icon);
        this.attackType = attackType;
        this.speed = 0.5f;
    }
    
    public AttackType getAttackType(){
        return attackType;
    }
    public float getSpeed(){
        return speed;
    }
}
