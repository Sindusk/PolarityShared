package netdata;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;

/**
 *
 * @author SinisteRing
 */
@Serializable
public class PlayerIDData extends AbstractMessage {
    private int id = -1;
    public PlayerIDData() {}
    public PlayerIDData(int ID){
        id = ID;
        this.setReliable(true);
    }
    public int getID(){
        return id;
    }
}
