package status;

import character.LivingCharacter;

/**
 *
 * @author SinisteRing
 */
public abstract class Status {
    protected float timer = 0;
    protected float duration;
    protected boolean finished = false;
    public Status(){}
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
    
    public void onApply(LivingCharacter tar){}
    public void onTick(LivingCharacter tar, float tpf){}
    public void onFinish(LivingCharacter tar){}
    
    public abstract Status merge(Status other);
}
