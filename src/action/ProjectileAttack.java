/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package action;

import character.CharacterManager;
import character.GameCharacter;
import com.jme3.math.Vector2f;
import com.jme3.network.serializing.Serializable;
import items.Weapon;
import netdata.ProjectileData;

/**
 * Action which causes a Projectile to spawn. Within this class holds all the data required for proper
 * execution of the Projectile when its event (collision or otherwise) is called.
 * @author Sindusk
 */
@Serializable
public class ProjectileAttack extends Action {
    protected Weapon weapon;
    protected float speed;
    
    public ProjectileAttack(GameCharacter owner, Vector2f start, Vector2f target, Weapon weapon, boolean down){
        super(owner, start, target);
        this.weapon = weapon;
        this.speed = weapon.getSpeed();
        this.target = target;
    }
    public ProjectileAttack(CharacterManager characterManager, ProjectileData data){
        super(characterManager.getPlayer(data.getOwner()), data.getStart(), data.getTarget());
        this.weapon = data.getWeapon();
        this.speed = weapon.getSpeed();
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
