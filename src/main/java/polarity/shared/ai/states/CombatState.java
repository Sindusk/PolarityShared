package polarity.shared.ai.states;

import polarity.shared.character.Monster;
import polarity.shared.entity.LivingEntity;
import polarity.shared.netdata.updates.MonsterStateUpdate;
import polarity.shared.world.GameWorld;

/**
 *
 * @author SinisteRing
 */
public class CombatState extends MonsterState {
    protected LivingEntity target;
    
    public CombatState(Monster monster, LivingEntity target){
        super(monster);
        this.target = target;
        entity.getNameplate().updateName("Combat");
    }
    
    public LivingEntity getTarget(){
        return target;
    }
    
    public MonsterStateUpdate toMessage(){
        return new MonsterStateUpdate(monster.getID(), "Combat");
    }
    
    public void update(GameWorld world, float tpf){
        entity.updateRotation(target.getLocation());
        if(monster.attackReady(tpf)){
            if(world.clearShot(entity.getLocation(), target.getLocation())){
                monster.attack(world, target, tpf);
            }else{
                //start moving
            }
        }
    }
}
