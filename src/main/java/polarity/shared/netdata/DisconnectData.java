package polarity.shared.netdata;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;

/**
 *
 * @author SinisteRing
 */
@Serializable
public class DisconnectData extends AbstractMessage {
    private int id;
    public DisconnectData(){
        //
    }
    public DisconnectData(int id){
        this.id = id;
    }
    public int getID(){
        return id;
    }
}
