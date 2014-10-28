package spellforge.nodes;

import com.jme3.network.serializing.Serializable;

/**
 *
 * @author SinisteRing
 */
@Serializable
public class PowerConduitData extends ConduitData {
    public PowerConduitData(){} // For serialization
    public PowerConduitData(SpellNodeData data){
        super(data);
        type = "Power Conduit";
    }
    
    @Override
    public boolean canConnect(SpellNodeData data){
        if(data instanceof GeneratorData || data instanceof PowerConduitData || data instanceof ModifierData){
            return true;
        }
        return false;
    }
}
