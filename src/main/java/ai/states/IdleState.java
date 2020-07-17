package ai.states;

import character.Monster;
import com.jme3.math.Vector2f;
import netdata.updates.MonsterStateUpdate;
import tools.Util;
import world.World;

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
    
    public void update(World world, float tpf){
        // No update when idle
    }
}
