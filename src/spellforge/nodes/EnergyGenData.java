package spellforge.nodes;

import com.jme3.network.serializing.Serializable;

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
