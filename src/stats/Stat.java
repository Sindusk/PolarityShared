/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stats;

/**
 *
 * @author SinisteRing
 */
public abstract class Stat {
    protected String name;
    protected int priority;
    protected float base;
    protected float value;
    
    // Common initializer for a new stat
    public Stat(float base){
        this.base = base;
        this.value = base;
    }
    
    // Getters:
    public float getCurrent(){
        return value;
    }
    public float getBase(){
        return base;
    }
    
    // Adds onto the value, which is independent of the base.
    public void add(float amount){
        value += amount;
    }
    public void addBase(float amount){
        base += amount;
    }
    
    // Subtract function subtracts from the current value.
    public void subtract(float amount){
        value -= amount;
    }
    public void subtractBase(float amount){
        base -= amount;
    }
    // Reduce function serves to ensure that the value does not go below zero
    public void reduce(float amount){
        this.subtract(amount);
        if(value < 0){
            value = 0;
        }
    }
}
