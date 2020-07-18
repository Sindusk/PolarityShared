package polarity.shared.ai.states;

import polarity.shared.character.Monster;
import com.jme3.math.Vector2f;
import polarity.shared.netdata.updates.MonsterStateUpdate;
import polarity.shared.tools.Util;
import polarity.shared.world.GameWorld;

/**
 *
 * @author SinisteRing
 */
public class IdleState extends MonsterState {
    private Vector2f target;
    
    public IdleState(Monster monster){
        super(monster);
        target = monster.getLocation().add(new Vector2f(Util.randFloat(-1, 1), Util.randFloat(-1, 1)));
        entity.updateRotation(target);
        entity.getNameplate().updateName("Idle");
    }
    
    public MonsterStateUpdate toMessage(){
        return new MonsterStateUpdate(monster.getID(), "Idle");
    }
    
    public void update(GameWorld world, float tpf){
        // No update when idle
    }
}
