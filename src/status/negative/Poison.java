package status.negative;

import character.LivingCharacter;
import status.Status;

/**
 *
 * @author SinisteRing
 */
public class Poison extends Status {
    protected float dps;
    
    public Poison(float duration, float dps){
        super(duration);
        this.dps = dps;
    }
    
    public float getDPS(){
        return dps;
    }
    
    @Override
    public void onTick(LivingCharacter tar, float tpf){
        tar.damage(dps*tpf);
    }
    
    public Status merge(Status other){
        if(!(other instanceof Poison)){
            return this;
        }
        Poison o = (Poison) other;
        float oTimeLeft = o.getDuration()-o.getTimer();
        float oTotal = o.getDPS()*oTimeLeft;
        float tTotal = dps*duration;
        float higherDuration = Math.max(oTimeLeft, duration);
        return new Poison(higherDuration, (oTotal+tTotal)/higherDuration);
    }
}
