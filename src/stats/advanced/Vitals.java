package stats.advanced;

import netdata.DamageData;
import screens.Screen;
import stats.Energy;
import stats.Health;
import stats.Shield;
import tools.Sys;

/**
 *
 * @author SinisteRing
 */
public class Vitals {
    protected int id;
    protected boolean dead = false;
    protected Health health;
    protected Shield shield;
    protected Energy energy;
    
    public Vitals(int id){
        this.id = id;
        health = new Health(100, 100);
        shield = new Shield(100, 100);
        energy = new Energy(100, 100);
    }
    
    public Health getHealth(){
        return health;
    }
    public Shield getShield(){
        return shield;
    }
    public Energy getEnergy(){
        return energy;
    }
    
    public void damage(float value){
        if(shield.value() > 0){ // Check shields first
            float remainingShield = shield.subtract(value);    // Subtract from shields, and store how much shield is left
            if(remainingShield < 0){  // If shields went below zero, subtract remainder from health:
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
