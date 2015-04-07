package netdata;

import character.types.Owner;
import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;

/**
 *
 * @author SinisteRing
 */
@Serializable
public class ChatMessage extends AbstractMessage {
    protected Owner owner;
    protected String message;
    public ChatMessage(){}  // For serialization
    public ChatMessage(Owner owner, String message){
        this.owner = owner;
        this.message = message;
    }
    public Owner getOwner(){
        return owner;
    }
    public String getMessage(){
        return message;
    }
}
