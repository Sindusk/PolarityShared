package polarity.shared.spellforge.nodes.effect;

import polarity.shared.character.GameCharacter;
import com.jme3.network.serializing.Serializable;
import polarity.shared.entity.LivingEntity;
import polarity.shared.actions.Action;
import polarity.shared.items.creation.ItemFactory;
import java.util.HashMap;
import polarity.shared.spellforge.nodes.EffectData;
import polarity.shared.spellforge.nodes.SpellNodeData;
import polarity.shared.status.negative.Slow;

/**
 *
 * @author SinisteRing
 */
@Serializable
public class SlowEffectData extends EffectData {
    protected float moveMult = 1;
    protected float duration = 2;
    
    public SlowEffectData(){
        init();
    }
    public SlowEffectData(SpellNodeData data){
        super(data);
        init();
    }
    private void init(){
        name = "Slow Effect";
        icon = "slowEffect";
    }
    
    @Override
    public Action getAction(final float mult){
        return new Action(){
            @Override
            public boolean onCollide(GameCharacter owner, LivingEntity entity){
                entity.applyStatus(new Slow(duration*multiplier*mult, moveMult));
                return true;
            }
        };
    }
    
    @Override
    public HashMap<String,Float> genProperties(int level){
        properties = new HashMap();
        cost = ItemFactory.leveledRandomFloat(-2f, level, 1);
        properties.put("Cost", cost);
        moveMult = ItemFactory.leveledRandomFloat(-0.9f, level, 2);
        properties.put("Speed Mult", moveMult);
        duration = ItemFactory.leveledRandomFloat(2f, level, 2);
        properties.put("Duration", duration);
        return properties;
    }
}
