package polarity.shared.ai.states;

import polarity.shared.character.Monster;
import polarity.shared.entity.MonsterEntity;
import polarity.shared.netdata.updates.MonsterStateUpdate;
import polarity.shared.world.GameWorld;

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
    public abstract void update(GameWorld world, float tpf);
}
