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
public class MultiModData extends ModifierData {
    protected float count = 0;
    
    public MultiModData(){
        init();
    }
    public MultiModData(SpellNodeData data){
        super(data);
        init();
    }
    private void init(){
        name = "Multiplier Modifier";
    }
    
    @Override
    public String getIcon(){
        return "multiModifier";
    }
    
    @Override
    public HashMap<String,Float> genProperties(int level){
        super.genProperties(level);
        effectMult = ItemFactory.leveledRandomFloat(0.3f, level, 2);
        properties.put("Effect Multiplier", effectMult);
        count = ItemFactory.leveledRandomFloat(2, level, 0);
        properties.put("Count", count);
        return properties;
    }
    
    @Override
    public void modifyCore(CoreVals vals){
        super.modifyCore(vals);
        vals.m_count += count;
    }
}
