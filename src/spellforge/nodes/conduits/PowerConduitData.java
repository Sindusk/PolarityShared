package spellforge.nodes.conduits;

import spellforge.nodes.ConduitData;
import com.jme3.network.serializing.Serializable;
import spellforge.nodes.CoreData;
import spellforge.nodes.GeneratorData;
import spellforge.nodes.ModifierData;
import spellforge.nodes.SpellNodeData;

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
        if(data instanceof GeneratorData || data instanceof CoreData || data instanceof ModifierData || data instanceof PowerConduitData){
            return true;
        }
        return false;
    }
}
