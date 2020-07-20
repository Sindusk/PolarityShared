package polarity.shared.character.types;

import com.jme3.network.serializing.Serializable;

/**
 * Owner can be any type of entity. It holds the ID for the entity, as well as their type.
 * This allows us to access the correct owner.
 * @author Sindusk
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
