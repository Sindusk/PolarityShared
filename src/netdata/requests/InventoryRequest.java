package netdata.requests;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;

/**
 *
 * @author SinisteRing
 */
@Serializable
public class InventoryRequest extends AbstractMessage {
    protected int id;
    public InventoryRequest(){}   // For serialization
    public InventoryRequest(int id){
        this.id = id;
    }
    public int getID(){
        return id;
    }
}
