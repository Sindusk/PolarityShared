package entity;

import character.LivingCharacter;
import com.jme3.scene.Node;
import entity.nameplates.Nameplate;
import character.types.CharType;
import entity.floatingtext.FloatingTextManager;
import netdata.DamageData;
import network.ServerNetwork;
import stats.advanced.Vitals;
import status.Status;
import tools.Sys;

/**
 *
 * @author SinisteRing
 */
public class LivingEntity extends Entity {
    protected FloatingTextManager fTextManager;
    protected Nameplate nameplate;
    protected Vitals vitals = new Vitals();
    
    public LivingEntity(Node parent, LivingCharacter owner){
        super(parent, owner);
        this.owner = owner;
        this.type = CharType.LIVING;
        fTextManager = new FloatingTextManager();
        nameplate = new Nameplate(node, owner.getName(), true);
    }
    
    public Nameplate getNameplate(){
        return nameplate;
    }
    public Vitals getVitals(){
        return vitals;
    }
    
    @Override
    public void update(float tpf){
        super.update(tpf);
        nameplate.update(this);
        fTextManager.update(this, tpf);
    }
    
    public void applyStatus(Status status){
        ((LivingCharacter)owner).applyStatus(status);
    }
    public void damage(float value){
        vitals.damage(value);
        fTextManager.queue("damage", value);
        if(Sys.getNetwork() instanceof ServerNetwork){
            Sys.getNetwork().send(new DamageData(owner.getID(), type, value));
        }
    }
    
    @Override
    public void destroy(){
        super.destroy();
        nameplate.destroy();
    }
}
