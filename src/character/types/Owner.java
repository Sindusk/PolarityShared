package character.types;

import com.jme3.network.serializing.Serializable;

/**
 *
 * @author SinisteRing
 */
@Serializable
public class Owner {
    protected int id;
    protected CharType type;
    public Owner(){}    // For serialization
    public Owner(int id, CharType type){
        this.id = id;
        this.type = type;
    }
    public int getID(){
        return id;
    }
    public CharType getType(){
        return type;
    }
}
