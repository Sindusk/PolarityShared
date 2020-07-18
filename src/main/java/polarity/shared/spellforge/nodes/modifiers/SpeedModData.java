package polarity.shared.spellforge.nodes.modifiers;

import polarity.shared.spellforge.nodes.CoreVals;
import com.jme3.network.serializing.Serializable;
import polarity.shared.items.creation.ItemFactory;
import java.util.HashMap;
import polarity.shared.spellforge.nodes.ModifierData;
import polarity.shared.spellforge.nodes.SpellNodeData;

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
        icon = "speedModifier";
        name = "Speed Modifier";
    }
    
    @Override
    public HashMap<String,Float> genProperties(int level){
        super.genProperties(level);
        effectMult = ItemFactory.leveledRandomPoweredFloat(1f, 0.3f, level, 2);
        properties.put("Effect Multiplier", effectMult);
        speedMult = ItemFactory.leveledRandomPoweredFloat(1f, 0.6f, level, 2);
        properties.put("Speed Multiplier", speedMult);
        return properties;
    }
    
    @Override
    public void modifyCore(CoreVals vals){
        super.modifyCore(vals);
        vals.m_speed *= speedMult;
    }
}
