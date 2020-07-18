package polarity.shared.netdata;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;
import polarity.shared.character.types.CharType;

/**
 *
 * @author SinisteRing
 */
@Serializable
public class DamageData extends AbstractMessage {
    protected int id;
    protected CharType type;
    protected float value;
    public DamageData(){}
    public DamageData(int id, CharType type, float value){
        this.id = id;
        this.type = type;
        this.value = value;
        setReliable(false);
    }
    public int getID(){
        return id;
    }
    public CharType getType(){
        return type;
    }
    public float getValue(){
        return value;
    }
}
