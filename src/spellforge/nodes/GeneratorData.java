package spellforge.nodes;

import com.jme3.math.ColorRGBA;
import spellforge.nodes.conduits.PowerConduitData;
import com.jme3.network.serializing.Serializable;
import items.creation.ItemGenerator;
import java.util.ArrayList;
import java.util.HashMap;
import spellforge.PulseHandler;
import spellforge.SpellMatrix;

/**
 *
 * @author SinisteRing
 */
@Serializable
public class GeneratorData extends SpellNodeData {
    protected ArrayList<SpellNodeData> granted;
    protected float rate = 5f;
    protected float storedPower = 0;
    protected float maxPower = 100;
    
    public GeneratorData(){
        type = "Generator";
        typeColor = ColorRGBA.Green;
    }
    public GeneratorData(SpellNodeData data){
        super(data.getX(), data.getY(), data.getLocation());
        type = "Generator";
        typeColor = ColorRGBA.Green;
    }
    
    @Override
    public String getIcon(){
        return "generator";
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
            //data.addSource(this);
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
