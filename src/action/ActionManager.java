/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package action;

import tools.Sys;

/**
 *
 * @author SinisteRing
 */
public class ActionManager {
    public ActionManager(){
        //
    }
    
    public void addProjectileAttack(ProjectileAttack a){
        Sys.getWorld().addProjectile(a);
    }
    
    public void trigger(Action a){
        
    }
}
