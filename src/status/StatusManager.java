package status;

import character.LivingCharacter;
import java.util.HashMap;

/**
 * [Server-Side] Status Manager for each player.
 * Only triggers server-side, so can be left null for clients.
 * @author Sindusk
 */
public class StatusManager {
    protected HashMap<Class,Status> effects = new HashMap();
    protected LivingCharacter owner;
    
    public StatusManager(LivingCharacter owner){
        this.owner = owner;
    }
    
    public void apply(Status status){
        Class clazz = status.getClass();
        if(effects.containsKey(clazz)){
            Status other = effects.get(clazz);
            effects.put(clazz, other.merge(status));
        }else{
            effects.put(clazz, status);
            status.onApply(owner);
        }
    }
    
    public void update(float tpf){
        for(Class clazz : effects.keySet()){
            if(effects.get(clazz).update(tpf)){ // If the status is finished
                effects.get(clazz).onFinish(owner);
                effects.remove(clazz);
            }else{  // Status is not finished
                effects.get(clazz).onTick(owner, tpf);
            }
        }
    }
}
