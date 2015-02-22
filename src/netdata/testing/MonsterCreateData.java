package netdata.testing;

import com.jme3.math.Vector2f;
import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;

/**
 *
 * @author SinisteRing
 */
@Serializable
public class MonsterCreateData extends AbstractMessage {
    protected Vector2f loc;
    public MonsterCreateData(){}
    public MonsterCreateData(Vector2f loc){
        this.loc = loc;
    }
    public Vector2f getLocation(){
        return loc;
    }
}
