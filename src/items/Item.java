package items;

import items.data.ItemData;

/**
 *
 * @author SinisteRing
 */
public class Item {
    protected Inventory inv;
    protected ItemData data;
    
    public Item(Inventory inv, ItemData data){
        this.inv = inv;
        this.data = data;
    }
    
    public Inventory getInventory(){
        return inv;
    }
    public ItemData getData(){
        return data;
    }
}
