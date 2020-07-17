package spellforge.nodes.modifiers;

import spellforge.nodes.CoreVals;
import com.jme3.network.serializing.Serializable;
import items.creation.ItemFactory;
import java.util.HashMap;
import spellforge.nodes.ModifierData;
import spellforge.nodes.SpellNodeData;

/**
 *
 * @author SinisteRing
 */
@Serializable
public class RadiusModData extends ModifierData {
    protected float radiusMult = 1;
    
    public RadiusModData(){
        init();
    }
    public RadiusModData(SpellNodeData data){
        super(data);
        init();
    }
    private void init(){
        name = "Radius Modifier";
    }
    
    @Override
    public String getIcon(){
        return "radiusModifier";
    }
    
    @Override
    public HashMap<String,Float> genProperties(int level){
        super.genProperties(level);
        effectMult = ItemFactory.leveledRandomFloat(0.8f, level, 2);
        properties.put("Effect Multiplier", effectMult);
        radiusMult = ItemFactory.leveledRandomFloat(0.8f, level, 2);
        properties.put("Radius Multiplier", radiusMult);
        return properties;
    }
    
    @Override
    public void modifyCore(CoreVals vals){
        super.modifyCore(vals);
        vals.m_radiusMult *= radiusMult;
    }
}
