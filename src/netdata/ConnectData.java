package netdata;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;

/**
 *
 * @author SinisteRing
 */
@Serializable
public class ConnectData extends AbstractMessage {
    private String version;
    public ConnectData() {}
    public ConnectData(String ver){
        version = ver;
        this.setReliable(true);
    }
    public String GetVersion(){
        return version;
    }
}
