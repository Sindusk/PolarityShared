package spellforge.nodes.effect;

import com.jme3.network.serializing.Serializable;
import entity.LivingEntity;
import events.Action;
import items.creation.ItemGenerator;
import java.util.HashMap;
import spellforge.nodes.EffectData;
import spellforge.nodes.SpellNodeData;

/**
 *
 * @author SinisteRing
 */
@Serializable
public class DamageEffectData extends EffectData {
    protected float damage = 10;
    
    public DamageEffectData(){
        init();
    }
    public DamageEffectData(SpellNodeData data){
        super(data);
        init();
    }
    private void init(){
        name = "Damage Effect";
    }
    
    @Override
    public Action getAction(){
        return new Action(){
            @Override
            public boolean onCollide(LivingEntity entity){
                entity.damage(damage);
                return true;
            }
        };
    }
    
    @Override
    public HashMap<String,Float> genProperties(int level){
        properties = new HashMap();
        cost = ItemGenerator.leveledRandomFloat(-2f, level, 1);
        properties.put("Cost", cost);
        damage = ItemGenerator.leveledRandomFloat(10f, level, 1);
        properties.put("Damage", damage);
        return properties;
    }
}
