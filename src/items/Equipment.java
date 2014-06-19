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
    
    public Weapon getWeapon(){
        return weapon;
    }
}
