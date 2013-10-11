/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package character.stats;

/**
 *
 * @author SinisteRing
 */
public class StatWithMax extends Stat {
    protected float baseMax;    // The base cap for the stat.
    protected float max;        // The current cap for the stat.
    
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
    
    // Increase the cap by the given amount. Used for temporary cap increases.
    public void increaseMax(float amount){
        max += amount;
    }
    
    // Reduce the cap by the given amount.
    public void decreaseMax(float amount){
        max -= amount;
        if(value > max){
            value = max;
        }
    }
}
