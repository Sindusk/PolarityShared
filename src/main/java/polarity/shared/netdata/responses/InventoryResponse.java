package polarity.shared.netdata.responses;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;
import polarity.shared.items.Inventory;

/**
 *
 * @author SinisteRing
 */
@Serializable
public class InventoryResponse extends AbstractMessage {
    protected Inventory inventory;
    public InventoryResponse(){}
    public InventoryResponse(Inventory inventory){
        this.inventory = inventory;
    }
    public Inventory getItems(){
        return inventory;
    }
}
