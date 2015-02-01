package spellforge.nodes.effect;

import items.creation.ItemGenerator;
import java.util.HashMap;
import spellforge.nodes.EffectData;
import spellforge.nodes.SpellNodeData;

/**
 *
 * @author SinisteRing
 */
public class DamageEffectData extends EffectData {
    protected float damage = 10;
    
    public DamageEffectData(){
        name = "Damage Effect";
    }
    public DamageEffectData(SpellNodeData data){
        super(data);
        name = "Damage Effect";
    }
    
    @Override
    public HashMap<String,Float> genProperties(int level){
        properties = new HashMap();
        damage = ItemGenerator.leveledRandomFloat(10f, level, 1);
        properties.put("Damage", damage);
        return properties;
    }
}
