package character;

import entity.LivingEntity;
import stats.advanced.Vitals;

/**
 *
 * @author SinisteRing
 */
public class LivingCharacter extends GameCharacter {
    public Vitals getVitals(){
        return ((LivingEntity)entity).getVitals();
    }
    
    public void damage(float value){
        ((LivingEntity)entity).damage(value);
    }
}
