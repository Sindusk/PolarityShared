package spellforge.nodes.generators;

import com.jme3.network.serializing.Serializable;
import spellforge.nodes.GeneratorData;
import spellforge.nodes.SpellNodeData;

/**
 *
 * @author SinisteRing
 */
@Serializable
public class ManaGenData extends GeneratorData {
    public ManaGenData(){
        init();
    }
    public ManaGenData(SpellNodeData data){
        super(data);
        init();
    }
    private void init(){
        name = "Mana Generator";
    }
}
