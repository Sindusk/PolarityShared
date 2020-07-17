package ai;

import ai.states.CombatState;
import ai.states.IdleState;
import ai.states.MonsterState;
import character.Monster;
import entity.LivingEntity;
import entity.PlayerEntity;
import java.util.ArrayList;
import world.World;

/**
 * Server-driven AI Manager. Controls and organizes all AI-related functions.
 * * NOTE TO SELF: Multithread! Multithread! Multithread! Please.
 * @author SinisteRing
 */
public class AIManager {
    protected static float CHECK_INTERVAL = 2;
    
    public AIManager(){}
    
    private void updateState(World world, Monster monster){
        MonsterState state = monster.getState();
        if(!(state instanceof CombatState)){
            // Not in a Combat State
            LivingEntity target = world.findClosestEnemy(monster.getEntity(), monster.getLeashRange());
            if(target != null && target instanceof PlayerEntity){
                monster.setState(new CombatState(monster, target));
            }
        }else{
            // Currently in a Combat State
            CombatState cState = (CombatState) state;
            if(monster.getLocation().distance(cState.getTarget().getLocation()) > monster.getLeashRange()){
                monster.setState(new IdleState(monster));
            }
        }
    }
    
    // [Server-Side] Updates the state of all mobs
    public void serverUpdate(World world, ArrayList<Monster> monsters, float tpf){
        for(Monster monster : monsters){
            monster.addStateCheckTime(tpf);
            if(monster.getStateCheckTimer() > CHECK_INTERVAL){
                monster.subStateCheckTime(CHECK_INTERVAL);
                updateState(world, monster);
            }
        }
    }
}
