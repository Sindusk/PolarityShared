/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package action;

import character.GameCharacter;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import items.Weapon;
import tools.Sys;
import tools.Util;

/**
 *
 * @author SinisteRing
 */
public class ProjectileAttack extends Action {
    protected Weapon weapon;
    protected float speed;
    
    public ProjectileAttack(GameCharacter owner, Vector2f start, Vector2f target, Weapon weapon, boolean down){
        super(owner, start, target);
        this.weapon = weapon;
        this.speed = weapon.getSpeed();
        Vector3f worldTarget = Util.getWorldLoc(target, Sys.getCamera());
        this.target = new Vector2f(worldTarget.x, worldTarget.y);
    }
    
    public Vector2f getStart(){
        return owner.getLocation();
    }
    public Vector2f getTarget(){
        return target;
    }
    public float getSpeed(){
        return speed;
    }
    
    public void onCollide(){}
}
