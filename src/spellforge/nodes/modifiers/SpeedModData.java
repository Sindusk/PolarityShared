package spellforge.nodes.modifiers;

import com.jme3.network.serializing.Serializable;
import items.creation.ItemGenerator;
import java.util.HashMap;
import spellforge.nodes.ModifierData;
import spellforge.nodes.SpellNodeData;

/**
 *
 * @author SinisteRing
 */
@Serializable
public class SpeedModData extends ModifierData {
    protected float speedMult = 1;
    
    public SpeedModData(){
        init();
    }
    public SpeedModData(SpellNodeData data){
        super(data);
        init();
    }
    private void init(){
        name = "Speed Modifier";
    }
    
    @Override
    public HashMap<String,Float> genProperties(int level){
        super.genProperties(level);
        effectMult = ItemGenerator.leveledRandomFloat(1f, level, 2);
        properties.put("Effect Multiplier", effectMult);
        speedMult = ItemGenerator.leveledRandomFloat(1f, level, 2);
        properties.put("Speed Multiplier", speedMult);
        return properties;
    }
}
