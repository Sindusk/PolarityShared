/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package character;

import com.jme3.math.Vector2f;
import entity.Entity;

/**
 *
 * @author SinisteRing
 */
public abstract class GameCharacter {
    protected Entity entity;
    
    public Entity getEntity(){
        return entity;
    }
    public Vector2f getLocation(){
        return entity.getLocation();
    }
    
    public void update(float tpf){
        entity.update(tpf);
    }
}
