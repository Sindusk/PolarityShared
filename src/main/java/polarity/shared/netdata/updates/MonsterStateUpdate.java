package polarity.shared.netdata.updates;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;

/**
 *
 * @author SinisteRing
 */
@Serializable
public class MonsterStateUpdate extends AbstractMessage {
    public int id;
    public String state;
    public MonsterStateUpdate(){}
    public MonsterStateUpdate(int id, String state){
        this.id = id;
        this.state = state;
    }
    public int getID(){
        return id;
    }
    public String getStateName(){
        return state;
    }
}
