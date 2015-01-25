package netdata.destroyers;

import netdata.*;
import com.jme3.math.Vector2f;
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
