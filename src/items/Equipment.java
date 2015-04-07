package items;

import items.data.equipment.WeaponItemData;
import com.jme3.network.serializing.Serializable;

/**
 *
 * @author SinisteRing
 */
@Serializable
public class Equipment {
    protected WeaponItemData weapon;
    
    public Equipment(){}
    public Equipment(WeaponItemData weapon){
        this.weapon = weapon;
    }
    
    public WeaponItemData getWeapon(){
        return weapon;
    }
    public void equipWeapon(WeaponItemData weapon){
        this.weapon = weapon;
    }
}
