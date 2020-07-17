package netdata.requests;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;

/**
 *
 * @author SinisteRing
 */
@Serializable
public class SpellMatrixRequest extends AbstractMessage {
    protected int id;
    public SpellMatrixRequest(){}   // For serialization
    public SpellMatrixRequest(int id){
        this.id = id;
    }
    public int getID(){
        return id;
    }
}
