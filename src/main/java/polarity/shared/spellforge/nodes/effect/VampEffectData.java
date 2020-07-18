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
public class VampEffectData extends EffectData {
    protected float heal = 5;
    
    public VampEffectData(){
        init();
    }
    public VampEffectData(SpellNodeData data){
        super(data);
        init();
    }
    private void init(){
        name = "Vamp Effect";
        icon = "vampEffect";
    }
    
    @Override
    public Action getAction(final float mult){
        return new Action(){
            @Override
            public boolean onCollide(GameCharacter owner, LivingEntity entity){
                if(owner.getEntity() instanceof LivingEntity){
                    LivingEntity ent = (LivingEntity) owner.getEntity();
                    ent.heal(heal*multiplier*mult);
                }
                return true;
            }
        };
    }
    
    @Override
    public HashMap<String,Float> genProperties(int level){
        properties = new HashMap();
        cost = ItemFactory.leveledRandomFloat(-2f, level, 1);
        properties.put("Cost", cost);
        heal = ItemFactory.leveledRandomFloat(5f, level, 1);
        properties.put("Heal", heal);
        return properties;
    }
}
