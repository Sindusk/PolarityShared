package stats.advanced;

import stats.Health;
import stats.Shield;

/**
 *
 * @author SinisteRing
 */
public class Vitals {
    protected boolean dead = false;
    protected Health health;
    protected Shield shield;
    
    public Vitals(){
        health = new Health(100, 100);
        shield = new Shield(50, 50);
    }
    
    public Health getHealth(){
        return health;
    }
    public Shield getShield(){
        return shield;
    }
    
    public void heal(float value){
        if(health.value() < health.getMax()){
            health.add(value);
        }
    }
    public void damage(float value){
        if(shield.value() > 0){ // Check shields first
            float remainingShield = shield.subtract(value);    // Subtract from shields, and store how much shield is left
            if(remainingShield < 0){  // If shields went below zero, subtract remainder from health:
                shield.subtract(remainingShield);
                float remainingHealth = health.subtract(-remainingShield);  // Subtract the passthrough damage from health.
                if(remainingHealth < 0){    // If health is below zero, you're dead.
                    dead = true;
                }
            }
        }else{
            health.subtract(value);
            if(health.value() < 0){
                dead = true;
            }
        }
    }
}
