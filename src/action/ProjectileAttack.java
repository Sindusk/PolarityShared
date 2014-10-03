/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package action;

import character.GameCharacter;
import com.jme3.math.Vector2f;
import items.Weapon;

/**
 *
 * @author SinisteRing
 */
public class ProjectileAttack extends Action {
    protected Weapon weapon;
    protected float speed;
    
    public ProjectileAttack(GameCharacter owner, Vector2f start, Vector2f target, Weapon weapon, float speed, boolean down){
        super(owner, start, target);
        this.weapon = weapon;
        this.speed = speed;
    }
    
    public Vector2f getStart(){
        return owner.getLocation();
    }
    
    public void onCollide(){}
}
