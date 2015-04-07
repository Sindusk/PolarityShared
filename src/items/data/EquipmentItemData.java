package items.data;

import items.Inventory;

/**
 *
 * @author Sindusk
 */
public class EquipmentItemData extends ItemData {
    public EquipmentItemData(){}
    public EquipmentItemData(Inventory inv, int itemLevel, String icon){
        super(inv, itemLevel, icon);
    }
}
