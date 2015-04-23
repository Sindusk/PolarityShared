package status.negative;

import character.LivingCharacter;
import character.data.Attributes;
import com.jme3.network.serializing.Serializable;
import status.Status;

/**
 *
 * @author Sindusk
 */
@Serializable
public class Slow extends Status {
    protected float multiplier;
    
    public Slow(){} // For serialization
    public Slow(float duration, float multiplier){
        super(duration);
        this.multiplier = multiplier;
    }
    
    public float getMultiplier(){
        return multiplier;
    }
    
    @Override
    public void modifyCharStats(Attributes charStats){
        charStats.get("Movement Speed").multiply(multiplier);
    }
    
    @Override
    public void onApply(LivingCharacter tar){
        tar.getCharStats().addModifier(this);
    }
    
    @Override
    public void onFinish(LivingCharacter tar){
        super.onFinish(tar);
        tar.getCharStats().removeModifier(this);
    }
    
    public Status merge(Status other){
        if(!(other instanceof Slow)){
            return this;
        }
        Slow o = (Slow) other;
        float oTimeLeft = o.getDuration()-o.getTimer();
        float sumTime = oTimeLeft + duration;
        float powerThis = duration / sumTime;
        float powerOther = oTimeLeft / sumTime;
        float multThis = multiplier * powerThis;
        float multOther = o.getMultiplier() * powerOther;
        float newMult = multThis + multOther;
        float higherDuration = Math.max(oTimeLeft, duration);
        return new Slow(higherDuration, newMult);
    }
}
