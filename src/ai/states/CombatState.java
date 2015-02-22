package ai.states;

import character.Monster;
import entity.LivingEntity;
import netdata.updates.MonsterStateUpdate;
import world.World;

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
    
    public void update(World world, float tpf){
        entity.updateRotation(target.getLocation());
        if(world.clearShot(entity.getLocation(), target.getLocation())){
            monster.attack(world, target, tpf);
        }
    }
}
