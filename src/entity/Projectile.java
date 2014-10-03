/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import action.ProjectileAttack;
import com.jme3.math.Vector2f;
import com.jme3.scene.Node;

/**
 *
 * @author SinisteRing
 */
public class Projectile extends Entity{
    protected ProjectileAttack attack;
    
    public Projectile(Node parent, ProjectileAttack attack){
        super(parent);
        this.attack = attack;
    }
    
    public void create(Vector2f start){
        
    }
    
    @Override
    public void update(float tpf){
        super.update(tpf);
    }
}
