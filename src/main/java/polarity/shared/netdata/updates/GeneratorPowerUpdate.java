package polarity.shared.netdata.updates;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;
import polarity.shared.tools.Vector2i;

/**
 *
 * @author SinisteRing
 */
@Serializable
public class GeneratorPowerUpdate extends AbstractMessage {
    protected int slot;
    protected Vector2i index;
    protected float power;
    public GeneratorPowerUpdate(){}
    public GeneratorPowerUpdate(int slot, Vector2i index, float power){
        this.slot = slot;
        this.index = index;
        this.power = power;
        setReliable(false);
    }
    public int getSlot(){
        return slot;
    }
    public Vector2i getIndex(){
        return index;
    }
    public float getPower(){
        return power;
    }
}
