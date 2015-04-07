package netdata;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;
import character.types.Owner;
import status.Status;

/**
 *
 * @author SinisteRing
 */
@Serializable
public class StatusData extends AbstractMessage {
    protected Owner owner;
    protected Status status;
    public StatusData(){}
    public StatusData(Owner owner, Status status){
        this.owner = owner;
        this.status = status;
    }
    public Owner getOwner(){
        return owner;
    }
    public Status getStatus(){
        return status;
    }
}
