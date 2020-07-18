package polarity.shared.netdata;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;

/**
 *
 * @author SinisteRing
 */
@Serializable
public class PingData extends AbstractMessage {
    public PingData(){
        setReliable(false);
    }
}
