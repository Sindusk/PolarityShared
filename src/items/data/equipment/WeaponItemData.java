package items.data.equipment;

import com.jme3.network.serializing.Serializable;
import items.Inventory;
import items.data.EquipmentItemData;

/**
 *
 * @author SinisteRing
 */
@Serializable
public class WeaponItemData extends EquipmentItemData {
    public WeaponItemData(){}
    public WeaponItemData(Inventory inv, int itemLevel, String icon){
        super(inv, itemLevel, icon);
    }
}
