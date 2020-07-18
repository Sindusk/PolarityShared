package polarity.shared.stats;

import com.jme3.network.serializing.Serializable;

/**
 *
 * @author SinisteRing
 */
@Serializable
public abstract class Stat {
    protected String name;
    protected int priority;
    protected float base;
    protected float value;
    
    // Common initializer for a new stat
    public Stat(){}
    public Stat(float base){
        this.base = base;
        this.value = base;
    }
    
    // Getters:
    public float value(){
        return value;
    }
    public float getBase(){
        return base;
    }
    
    public void reset(){
        value = base;
    }
    public void set(float amount){
        value = amount;
    }
    
    // Adds onto the value, which is independent of the base.
    public void add(float amount){
        value += amount;
    }
    public void addBase(float amount){
        base += amount;
    }
    
    // Subtract function subtracts from the current value.
    public float subtract(float amount){
        value -= amount;
        return value;
    }
    public float subtractBase(float amount){
        base -= amount;
        return base;
    }
    // Reduce function serves to ensure that the value does not go below zero
    public void reduce(float amount){
        this.subtract(amount);
        if(value < 0){
            value = 0;
        }
    }
    
    public void multiply(float amount){
        value *= amount;
    }
}
