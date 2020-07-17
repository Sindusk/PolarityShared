package ai.states;

import character.Monster;
import entity.MonsterEntity;
import netdata.updates.MonsterStateUpdate;
import world.World;

/**
 *
 * @author SinisteRing
 */
public abstract class MonsterState {
    protected Monster monster;
    protected MonsterEntity entity;
    public MonsterState(Monster monster){
        this.monster = monster;
        this.entity = (MonsterEntity) monster.getEntity();
    }
    
    public abstract MonsterStateUpdate toMessage();
    public abstract void update(World world, float tpf);
}
