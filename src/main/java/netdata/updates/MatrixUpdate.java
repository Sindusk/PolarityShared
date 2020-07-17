package netdata.updates;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;
import spellforge.nodes.SpellNodeData;

/**
 *
 * @author SinisteRing
 */
@Serializable
public class MatrixUpdate extends AbstractMessage {
    protected int id;
    protected int slot;
    protected SpellNodeData data;
    public MatrixUpdate(){}
    public MatrixUpdate(int id, int slot, SpellNodeData data){
        this.id = id;
        this.slot = slot;
        this.data = data;
    }
    public int getID(){
        return id;
    }
    public int getSlot(){
        return slot;
    }
    public SpellNodeData getData(){
        return data;
    }
}
