package spellforge.nodes;

import com.jme3.math.ColorRGBA;
import com.jme3.network.HostedConnection;
import spellforge.nodes.conduits.PowerConduitData;
import com.jme3.network.serializing.Serializable;
import items.creation.ItemGenerator;
import java.util.HashMap;
import netdata.updates.GeneratorPowerUpdate;
import spellforge.PulseHandler;
import spellforge.SpellMatrix;

/**
 *
 * @author SinisteRing
 */
@Serializable
public class GeneratorData extends SpellNodeData {
    protected float rate = 5f;
    protected float storedPower = 0;
    protected float maxPower = 100;
    
    public GeneratorData(){
        init();
    }
    public GeneratorData(SpellNodeData data){
        super(data.getX(), data.getY(), data.getLocation());
        init();
    }
    private void init(){
        type = "Generator";
        typeColor = ColorRGBA.Green;
    }
    
    @Override
    public String getIcon(){
        return "generator";
    }
    public float getStoredPower(){
        return storedPower;
    }
    
    public void setStoredPower(float amount){
        this.storedPower = amount;
    }
    
    public void subtractPower(float amount){
        storedPower -= amount;
    }
    public void subtractPower(HostedConnection conn, int slot, float amount){
        subtractPower(amount);
        conn.send(new GeneratorPowerUpdate(slot, index, storedPower));
    }
    
    @Override
    public String getText(){
        return ""+Math.round(storedPower*100f)/100f;
    }
    
    @Override
    public boolean canProvide(SpellNodeData data){
        if(data instanceof CoreData){
            return true;
        }else if(data instanceof EffectData){
            return true;
        }else if(data instanceof ModifierData){
            return true;
        }
        return false;
    }
    
    @Override
    public boolean canConnect(SpellNodeData data){
        if(data instanceof PowerConduitData){
            return true;
        }
        return false;
    }
    @Override
    public boolean canTravel(SpellNodeData data){
        if(data instanceof PowerConduitData){
            return true;
        }
        return false;
    }
    
    @Override
    public HashMap<String,Float> getSpellNodeProperties(){
        float roundedPower = Math.round(storedPower*100f)/100f;
        properties.put("Stored", roundedPower);
        return properties;
    }
    @Override
    public HashMap<String,Float> genProperties(int level){
        properties = new HashMap();
        rate = ItemGenerator.leveledRandomFloat(5f, level, 2);
        properties.put("Power Rate", rate);
        maxPower = ItemGenerator.leveledRandomFloat(100f, level, 1);
        properties.put("Maximum Power", maxPower);
        return properties;
    }
    
    @Override
    public void recalculate(SpellMatrix matrix){
        PulseHandler handler = new PulseHandler(matrix, this);
        handler.createPulse(this);
        granted = handler.getGranted();
        for(SpellNodeData data : granted){
            if(data instanceof PowerableData){
                PowerableData p = (PowerableData) data;
                p.addPowerSource(this);
            }
        }
    }
    
    @Override
    public void update(float tpf){
        storedPower += rate*tpf;
        if(storedPower > maxPower){
            storedPower = maxPower;
        }
    }
}
