package spellforge.nodes;

import com.jme3.network.serializing.Serializable;

/**
 *
 * @author SinisteRing
 */
@Serializable
public class ModifierConduitData extends ConduitData {
    public ModifierConduitData(){}  // For serialization
    public ModifierConduitData(SpellNodeData data){
        super(data);
        type = "Modifier Conduit";
    }
    
    @Override
    public boolean canConnect(SpellNodeData data){
        if(data instanceof CoreData || data instanceof ModifierData || data instanceof ModifierConduitData){
            return true;
        }
        return false;
    }
}
