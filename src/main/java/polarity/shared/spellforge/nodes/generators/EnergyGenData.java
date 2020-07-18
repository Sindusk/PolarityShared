package polarity.shared.spellforge.nodes.generators;

import com.jme3.network.serializing.Serializable;
import polarity.shared.spellforge.nodes.GeneratorData;
import polarity.shared.spellforge.nodes.SpellNodeData;

/**
 *
 * @author SinisteRing
 */
@Serializable
public class EnergyGenData extends GeneratorData {
    public EnergyGenData(){
        init();
    }
    public EnergyGenData(SpellNodeData data){
        super(data);
        init();
    }
    private void init(){
        name = "Energy Generator";
    }
}
