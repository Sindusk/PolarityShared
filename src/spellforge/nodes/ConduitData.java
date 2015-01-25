package spellforge.nodes;

import com.jme3.network.serializing.Serializable;
import spellforge.nodes.SpellNodeData;

/**
 *
 * @author SinisteRing
 */
@Serializable
public class ConduitData extends SpellNodeData {
    public ConduitData(){}  // For serialization
    public ConduitData(SpellNodeData data){
        super(data.getX(), data.getY(), data.getLocation());
    }
}
