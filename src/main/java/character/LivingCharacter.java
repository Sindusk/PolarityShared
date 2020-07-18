package character;

import entity.LivingEntity;
import java.util.HashMap;
import netdata.StatusData;
import netdata.destroyers.DestroyStatusData;
import stats.advanced.Vitals;
import status.Status;
import status.StatusManager;
import tools.Sys;
import tools.Util;

/**
 *
 * @author SinisteRing
 */
public class LivingCharacter extends GameCharacter {
    protected float moveSpeedMult = 1;
    protected HashMap<Status,Float> moveMods = new HashMap();
    protected StatusManager statusManager;
    
    public LivingCharacter(int id, String name){
        super(id);
        this.statusManager = new StatusManager(this);
        this.name = name;
    }
    
    public Vitals getVitals(){
        return ((LivingEntity)entity).getVitals();
    }
    
    public boolean isEnemy(GameCharacter other){
        Util.log("[LivingCharacter] <isEnemy> Critical Error: Not overridden!");
        return false;
    }
    
    @Override
    public void update(float tpf){
        statusManager.update(tpf);
    }
    public void serverUpdate(float tpf){
        statusManager.serverUpdate(tpf);
    }
    
    public void updateMovementSpeed(){
        moveSpeedMult = 1;
        for(Status s : moveMods.keySet()){
            moveSpeedMult *= moveMods.get(s);
        }
    }
    public void applyStatus(Status status){
        statusManager.apply(status);
        if(!Sys.getNetwork().isClient()){ // Server Message
            Sys.getNetwork().send(new StatusData(this.asOwner(), status));
        }
    }
    public void removeStatus(Status status){
        statusManager.remove(status);
        if(!Sys.getNetwork().isClient()){ // Server Message
            Sys.getNetwork().send(new DestroyStatusData(this.asOwner(), status));
        }
    }
    public void heal(float value){
        ((LivingEntity)entity).heal(value);
    }
    public void damage(float value){
        ((LivingEntity)entity).damage(value);
    }
}
