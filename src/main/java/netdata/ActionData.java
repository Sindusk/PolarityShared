package netdata;

import com.jme3.math.Vector2f;
import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;

/**
 *
 * @author SinisteRing
 */
@Serializable
public class ActionData extends AbstractMessage {
    protected int id;
    protected int slot;
    protected Vector2f start;
    protected Vector2f target;
    public ActionData(){}   // For serialization
    public ActionData(int id, int slot, Vector2f start, Vector2f target){
        this.id = id;
        this.slot = slot;
        this.start = start;
        this.target = target;
    }
    public int getID(){
        return id;
    }
    public int getSlot(){
        return slot;
    }
    public Vector2f getStart(){
        return start;
    }
    public Vector2f getTarget(){
        return target;
    }
}
