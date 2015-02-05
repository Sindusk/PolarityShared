package entity;

import character.LivingCharacter;
import com.jme3.scene.Node;
import netdata.DamageData;
import network.ServerNetwork;
import stats.advanced.Vitals;
import tools.Sys;

/**
 *
 * @author SinisteRing
 */
public class LivingEntity extends Entity {
    protected LivingCharacter owner;
    protected Vitals vitals = new Vitals();
    
    public LivingEntity(Node parent, LivingCharacter owner){
        super(parent);
        this.owner = owner;
    }
    
    public Vitals getVitals(){
        return vitals;
    }
    
    public void damage(float value){
        vitals.damage(value);
        if(Sys.getNetwork() instanceof ServerNetwork){
            Sys.getNetwork().send(new DamageData(owner.getID(), value));
        }
    }
}
