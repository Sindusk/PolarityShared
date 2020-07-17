package netdata.destroyers;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;

/**
 *
 * @author SinisteRing
 */
@Serializable
public class DestroyProjectileData extends AbstractMessage {
    protected int hashCode;
    public DestroyProjectileData(){}   // For serialization
    public DestroyProjectileData(int hashCode){
        this.hashCode = hashCode;
    }
    public int getHashCode(){
        return hashCode;
    }
}
