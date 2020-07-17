package status;

import character.LivingCharacter;
import character.data.Attributes;
import com.jme3.network.serializing.Serializable;

/**
 *
 * @author SinisteRing
 */
@Serializable
public abstract class Status {
    protected float timer = 0;
    protected float duration;
    protected boolean finished = false;
    
    public Status(){}   // For serialization
    public Status(float duration){
        this.duration = duration;
    }
    
    public float getDuration(){
        return duration;
    }
    public float getTimer(){
        return timer;
    }
    
    public boolean update(float tpf){
        timer += tpf;
        if(timer > duration){
            finished = true;
        }
        return finished;
    }
    
    public void modifyCharStats(Attributes stats){}
    public void onApply(LivingCharacter tar){}
    public void onServerTick(LivingCharacter tar, float tpf){}
    public void onClientTick(LivingCharacter tar, float tpf){}
    public void onFinish(LivingCharacter tar){
        tar.removeStatus(this);
    }
    
    public abstract Status merge(Status other);
}
