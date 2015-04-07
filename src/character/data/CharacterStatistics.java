package character.data;

import com.jme3.network.serializing.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import stats.MovementSpeed;
import stats.Stat;
import status.Status;

/**
 *
 * @author Sindusk
 */
@Serializable
public class CharacterStatistics {
    protected HashMap<String,Stat> stats = new HashMap();
    protected ArrayList<Status> modifiers = new ArrayList();
    
    protected MovementSpeed moveSpeed = new MovementSpeed(3);
    
    private void reset(){
        for(String s : stats.keySet()){
            stats.get(s).reset();
        }
    }
    public CharacterStatistics(){
        stats.put("Movement Speed", moveSpeed);
    }
    
    public Stat get(String key){
        return stats.get(key);
    }
    
    protected void recalculate(){
        reset();
        for(Status status : modifiers){
            status.modifyCharStats(this);
        }
    }
    
    public void addModifier(Status status){
        modifiers.add(status);
        recalculate();
    }
    public void removeModifier(Status status){
        modifiers.remove(status);
        recalculate();
    }
    
    public float getMovementSpeed(){
        return moveSpeed.value();
    }
}
