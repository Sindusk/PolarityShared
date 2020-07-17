package netdata.destroyers;

import character.types.Owner;
import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;
import status.Status;

/**
 *
 * @author SinisteRing
 */
@Serializable
public class DestroyStatusData extends AbstractMessage {
    protected Owner owner;
    protected Status status;
    public DestroyStatusData(){}   // For serialization
    public DestroyStatusData(Owner owner, Status status){
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
