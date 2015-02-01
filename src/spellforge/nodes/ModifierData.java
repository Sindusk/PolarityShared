package spellforge.nodes;

import com.jme3.math.ColorRGBA;
import com.jme3.network.serializing.Serializable;
import items.creation.ItemGenerator;
import java.util.ArrayList;
import java.util.HashMap;
import spellforge.PulseHandler;
import spellforge.SpellMatrix;
import spellforge.nodes.conduits.ModifierConduitData;

/**
 *
 * @author SinisteRing
 */
@Serializable
public class ModifierData extends SpellNodeData {
    protected ArrayList<SpellNodeData> granted;
    protected float effectMult = 1;
    protected float multiplier = 0;
    
    public ModifierData(){
        type = "Modifier";
        typeColor = ColorRGBA.Blue;
    }
    public ModifierData(SpellNodeData data){
        super(data.getX(), data.getY(), data.getLocation());
        type = "Modifier";
        typeColor = ColorRGBA.Blue;
    }
    
    @Override
    public String getIcon(){
        return "modifier";
    }
    
    @Override
    public boolean canProvide(SpellNodeData data){
        if(data instanceof CoreData){
            return true;
        }
        return false;
    }
    @Override
    public boolean canConnect(SpellNodeData data){
        if(data instanceof ConduitData){
            return true;
        }
        return false;
    }
    @Override
    public boolean canTravel(SpellNodeData data){
        if(data instanceof ModifierConduitData){
            return true;
        }
        return false;
    }
    
    @Override
    public String getText(){
        return Math.round(multiplier*100f)+"%";
    }
    
    @Override
    public HashMap<String,Float> genProperties(int level){
        properties = new HashMap();
        effectMult = ItemGenerator.leveledRandomFloat(35f, level, 1);
        properties.put("Effect Multiplier", effectMult);
        return properties;
    }
    
    @Override
    public void recalculate(SpellMatrix matrix){
        PulseHandler handler = new PulseHandler(matrix, this);
        handler.createPulse(this);
        granted = handler.getGranted();
        if(granted.size() > 0){
            multiplier = Math.min(1, 2f/granted.size());
        }
        CoreData coreData;
        for(SpellNodeData data : granted){
            if(data instanceof CoreData){
                coreData = (CoreData) data;
                coreData.addModifier(this, multiplier);
            }
        }
    }
    
    @Override
    public void update(float tpf){
        // Not Needed
    }
}
