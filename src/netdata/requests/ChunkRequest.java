package netdata.requests;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;
import tools.Vector2i;

/**
 *
 * @author SinisteRing
 */
@Serializable
public class ChunkRequest extends AbstractMessage {
    protected Vector2i key;
    public ChunkRequest(){}
    public ChunkRequest(Vector2i key){
        this.key = key;
    }
    public Vector2i getKey(){
        return key;
    }
}
