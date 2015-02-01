package spellforge.nodes.conduits;

import spellforge.nodes.ConduitData;
import com.jme3.network.serializing.Serializable;
import spellforge.nodes.CoreData;
import spellforge.nodes.ModifierData;
import spellforge.nodes.SpellNodeData;

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
    public String getIcon(){
        return "modifierConduit";
    }
    
    @Override
    public boolean canConnect(SpellNodeData data){
        if(data instanceof CoreData || data instanceof ModifierData || data instanceof ModifierConduitData){
            return true;
        }
        return false;
    }
}
