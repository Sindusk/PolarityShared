package spellforge.nodes;

import com.jme3.network.serializing.Serializable;

/**
 *
 * @author SinisteRing
 */
@Serializable
public class DamageModifierData extends ModifierData {
    public DamageModifierData(){}   // For serialization
    public DamageModifierData(SpellNodeData data){
        super(data);
        type = "Damage Modifier";
    }
}
