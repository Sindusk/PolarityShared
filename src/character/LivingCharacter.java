package character;

import entity.LivingEntity;
import stats.advanced.Vitals;
import status.Status;
import status.StatusManager;
import tools.Util;

/**
 *
 * @author SinisteRing
 */
public class LivingCharacter extends GameCharacter {
    protected StatusManager statusManager;
    protected String name;
    
    public LivingCharacter(int id, String name){
        super(id);
        this.statusManager = new StatusManager(this);
        this.name = name;
    }
    
    public String getName(){
        return name;
    }
    public Vitals getVitals(){
        return ((LivingEntity)entity).getVitals();
    }
    
    public boolean isEnemy(GameCharacter other){
        Util.log("[LivingCharacter] <isEnemy> Critical Error: Not overridden!");
        return false;
    }
    
    public void serverUpdate(float tpf){
        statusManager.update(tpf);
    }
    
    public void applyStatus(Status status){
        statusManager.apply(status);
    }
    public void damage(float value){
        ((LivingEntity)entity).damage(value);
    }
}
