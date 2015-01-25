package spellforge.nodes.modifiers;

import com.jme3.network.serializing.Serializable;
import spellforge.nodes.ModifierData;
import spellforge.nodes.SpellNodeData;

/**
 *
 * @author SinisteRing
 */
@Serializable
public class MultiModData extends ModifierData {
    public MultiModData(){}   // For serialization
    public MultiModData(SpellNodeData data){
        super(data);
        type = "Multiplier Modifier";
    }
}
