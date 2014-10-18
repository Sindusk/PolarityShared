package netdata;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;

/**
 *
 * @author SinisteRing
 */
@Serializable
public class DamageData extends AbstractMessage {
    protected int id;
    protected float value;
    public DamageData(){}
    public DamageData(int id, float value){
        this.id = id;
        this.value = value;
    }
    public int getID(){
        return id;
    }
    public float getValue(){
        return value;
    }
}
