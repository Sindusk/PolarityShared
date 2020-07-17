package equipment;

import com.jme3.network.serializing.Serializable;

/**
 *
 * @author SinisteRing
 */
@Serializable
public class WeaponData extends EquipmentData {
    public WeaponData(){}
    public WeaponData(String icon){
        super(icon);
    }
}
