package polarity.shared.spellforge.nodes.effect;

import polarity.shared.character.GameCharacter;
import com.jme3.network.serializing.Serializable;
import polarity.shared.entity.LivingEntity;
import polarity.shared.actions.Action;
import polarity.shared.items.creation.ItemFactory;
import java.util.HashMap;
import polarity.shared.spellforge.nodes.EffectData;
import polarity.shared.spellforge.nodes.SpellNodeData;

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
        icon = "damageEffect";
    }
    
    @Override
    public Action getAction(final float mult){
        return new Action(){
            @Override
            public boolean onCollide(GameCharacter owner, LivingEntity entity){
                entity.damage(damage*multiplier*mult);
                return true;
            }
        };
    }
    
    @Override
    public HashMap<String,Float> genProperties(int level){
        properties = new HashMap();
        cost = ItemFactory.leveledRandomFloat(-2f, level, 1);
        properties.put("Cost", cost);
        damage = ItemFactory.leveledRandomFloat(10f, level, 1);
        properties.put("Damage", damage);
        return properties;
    }
}
