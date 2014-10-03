package items;

import com.jme3.network.serializing.Serializable;

/**
 *
 * @author SinisteRing
 */
@Serializable
public class Equipment {
    protected Weapon weapon;
    
    public Equipment(){}
    public Equipment(Weapon weapon){
        this.weapon = weapon;
    }
    
    public Weapon getWeapon(){
        return weapon;
    }
    public void equipWeapon(Weapon weapon){
        this.weapon = weapon;
    }
}
