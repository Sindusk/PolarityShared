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
    
    public Status get(Class clazz){
        return effects.get(clazz);
    }
    
    public void apply(Status status){
        Class clazz = status.getClass();
        if(effects.containsKey(clazz)){
            Status other = effects.get(clazz);
            other.onFinish(owner);
            effects.put(clazz, status.merge(other));
            effects.get(clazz).onApply(owner);
        }else{
            effects.put(clazz, status);
            status.onApply(owner);
        }
    }
    protected void endStatus(Class clazz){
        Status s = effects.get(clazz);
        effects.remove(clazz);
        s.onFinish(owner);
    }
    public void remove(Status status){
        Class clazz = status.getClass();
        if(effects.containsKey(clazz)){
            endStatus(clazz);
        }
    }
    
    public void update(float tpf){
        for(Class clazz : effects.keySet()){
            if(effects.get(clazz).update(tpf)){
                endStatus(clazz);
            }
        }
    }
    public void serverUpdate(float tpf){
        for(Class clazz : effects.keySet()){
            if(effects.get(clazz).update(tpf)){ // If the status is finished
                endStatus(clazz);
            }else{  // Status is not finished
                effects.get(clazz).onServerTick(owner, tpf);
            }
        }
    }
}
