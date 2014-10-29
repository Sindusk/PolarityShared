package spellforge.nodes;

import com.jme3.network.serializing.Serializable;

/**
 *
 * @author SinisteRing
 */
@Serializable
public class ModifierData extends PoweredNodeData {
    public ModifierData(){} // For serialization
    public ModifierData(SpellNodeData data){
        super(data);
    }
    
    @Override
    public boolean canConnect(SpellNodeData data){
        if(data instanceof ConduitData){
            return true;
        }
        return false;
    }
}
