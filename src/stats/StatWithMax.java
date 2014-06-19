/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stats;

import com.jme3.network.serializing.Serializable;

/**
 *
 * @author SinisteRing
 */
@Serializable
public class StatWithMax extends Stat {
    protected float baseMax;    // The base cap for the stat.
    protected float max;        // The current cap for the stat.
    
    public StatWithMax(){
        //
    }
    // Initialize with stat value and base stat cap.
    public StatWithMax(float amount, float max){
        super(amount);
        this.baseMax = max;
        this.max = max;
    }
    
    // Getters:
    public float getMax(){
        return max;
    }
    
    // Ensures that the capacity of the value is intact and fits within the range required.
    private void ensureCapacity(){
        if(value > max){
            value = max;
        }
    }
    
    // Increase the cap by the given amount. Used for temporary cap increases.
    public void increaseMax(float amount){
        max += amount;
    }
    
    // Reduce the cap by the given amount.
    public void decreaseMax(float amount){
        max -= amount;
        ensureCapacity();
    }
    
    // Override add method to ensure additions do not go over the limit.
    @Override
    public void add(float amount){
        value += amount;
        ensureCapacity();
    }
}
