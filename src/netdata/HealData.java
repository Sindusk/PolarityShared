package netdata;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;
import character.types.CharType;

/**
 *
 * @author SinisteRing
 */
@Serializable
public class HealData extends AbstractMessage {
    protected int id;
    protected CharType type;
    protected float value;
    public HealData(){}
    public HealData(int id, CharType type, float value){
        this.id = id;
        this.type = type;
        this.value = value;
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
