package polarity.shared.netdata;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;
import polarity.shared.network.ServerStatus;

/**
 *
 * @author SinisteRing
 */
@Serializable
public class ServerStatusData extends AbstractMessage {
    private ServerStatus status;
    public ServerStatusData() {}
    public ServerStatusData(ServerStatus status){
        this.status = status;
        this.setReliable(true);
    }
    public ServerStatus getStatus(){
        return status;
    }

}
