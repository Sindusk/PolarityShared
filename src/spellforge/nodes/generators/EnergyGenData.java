package spellforge.nodes.generators;

import com.jme3.network.serializing.Serializable;
import spellforge.nodes.GeneratorData;
import spellforge.nodes.SpellNodeData;

/**
 *
 * @author SinisteRing
 */
@Serializable
public class EnergyGenData extends GeneratorData {
    public EnergyGenData(){}    // For serialization
    public EnergyGenData(SpellNodeData data){
        super(data);
        type = "Energy Generator";
    }
}
