package netdata;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;

/**
 *
 * @author SinisteRing
 */
@Serializable
public class DevLogData extends AbstractMessage {
    protected String message;
    public DevLogData(){}
    public DevLogData(String message){
        this.message = message;
    }
    public String getMessage(){
        return message;
    }
}
