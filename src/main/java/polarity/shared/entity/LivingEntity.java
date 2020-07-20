package polarity.shared.entity;

import polarity.shared.character.LivingCharacter;
import com.jme3.scene.Node;
import polarity.shared.entity.nameplates.Nameplate;
import polarity.shared.character.types.CharType;
import com.jme3.math.ColorRGBA;
import polarity.shared.entity.floatingtext.FloatingTextManager;
import polarity.shared.netdata.DamageData;
import polarity.shared.netdata.HealData;
import polarity.shared.stats.advanced.Vitals;
import polarity.shared.status.Status;
import polarity.shared.tools.Sys;

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
    public void heal(float value){
        vitals.heal(value);
        fTextManager.queue("heal", ColorRGBA.Green, value);
        // TODO: Re-implement better.
        /*if(!Sys.getNetwork().isClient()){ // Server message
            Sys.getNetwork().send(new HealData(owner.getID(), type, value));
        }*/
    }
    public void damage(float value){
        vitals.damage(value);
        fTextManager.queue("damage", ColorRGBA.Red, value);
        // TODO: Re-implement better.
        /*if(!Sys.getNetwork().isClient()){ // Server message
            Sys.getNetwork().send(new DamageData(owner.getID(), type, value));
        }*/
    }
    
    @Override
    public void destroy(){
        super.destroy();
        nameplate.destroy();
    }
}
