package polarity.shared.spellforge.nodes.effect;

import polarity.shared.character.GameCharacter;
import com.jme3.network.serializing.Serializable;
import polarity.shared.entity.LivingEntity;
import polarity.shared.actions.Action;
import polarity.shared.items.creation.ItemFactory;
import java.util.HashMap;
import polarity.shared.spellforge.nodes.EffectData;
import polarity.shared.spellforge.nodes.SpellNodeData;
import polarity.shared.status.negative.Poison;

/**
 *
 * @author SinisteRing
 */
@Serializable
public class PoisonEffectData extends EffectData {
    protected float duration;
    protected float dps;
    
    public PoisonEffectData(){
        init();
    }
    public PoisonEffectData(SpellNodeData data){
        super(data);
        init();
    }
    private void init(){
        name = "Poison Effect";
        icon = "poisonEffect";
    }
    
    @Override
    public Action getAction(final float mult){
        return new Action(){
            @Override
            public boolean onCollide(GameCharacter owner, LivingEntity entity){
                entity.applyStatus(new Poison(duration, dps*multiplier*mult));
                return true;
            }
        };
    }
    
    @Override
    public HashMap<String,Float> genProperties(int level){
        properties = new HashMap();
        cost = ItemFactory.leveledRandomFloat(-3f, level, 1);
        properties.put("Cost", cost);
        dps = ItemFactory.leveledRandomFloat(5f, level, 1);
        properties.put("DPS", dps);
        duration = ItemFactory.leveledRandomFloat(3f, level, 1);
        properties.put("Duration", duration);
        return properties;
    }
}
