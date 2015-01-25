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
    private String name;
    public ConnectData() {}
    public ConnectData(String version, String name){
        this.version = version;
        this.name = name;
        this.setReliable(true);
    }
    public String getVersion(){
        return version;
    }
    public String getName(){
        return name;
    }
}
