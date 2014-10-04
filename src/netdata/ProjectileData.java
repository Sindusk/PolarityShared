/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package netdata;

import action.ProjectileAttack;
import com.jme3.math.Vector2f;
import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;
import items.Weapon;

/**
 *
 * @author SinisteRing
 */
@Serializable
public class ProjectileData extends AbstractMessage {
    protected int owner;
    protected Vector2f start;
    protected Vector2f target;
    protected Weapon weapon;
    public ProjectileData() {}
    public ProjectileData(int owner, Vector2f start, Vector2f target, Weapon weapon){
        this.owner = owner;
        this.start = start;
        this.target = target;
        this.weapon = weapon;
    }
    public int getOwner(){
        return owner;
    }
    public Vector2f getStart(){
        return start;
    }
    public Vector2f getTarget(){
        return target;
    }
    public Weapon getWeapon(){
        return weapon;
    }
}
