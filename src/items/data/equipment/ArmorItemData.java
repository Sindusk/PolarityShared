package items.data.equipment;

import items.Inventory;
import items.data.EquipmentItemData;

/**
 *
 * @author Sindusk
 */
public class ArmorItemData extends EquipmentItemData {
    public ArmorItemData(){}
    public ArmorItemData(Inventory inv, int itemLevel, String icon){
        super(inv, itemLevel, icon);
    }
}
