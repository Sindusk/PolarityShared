package spellforge.nodes;

import com.jme3.math.ColorRGBA;
import com.jme3.network.serializing.Serializable;
import items.creation.ItemFactory;
import java.util.HashMap;
import spellforge.PulseHandler;
import spellforge.SpellMatrix;
import spellforge.nodes.conduits.ModifierConduitData;
import tools.Util;

/**
 *
 * @author SinisteRing
 */
@Serializable
public class ModifierData extends SpellNodeData {
    protected float effectMult = 1;
    protected float multiplier = 1;
    protected float cost = 1;
    
    public ModifierData(){
        init();
    }
    public ModifierData(SpellNodeData data){
        super(data.getX(), data.getY(), data.getLocation());
        init();
    }
    private void init(){
        icon = "weapons";
        type = "Modifier";
        //typeColor = new ColorRGBA(0.93f, 0.92f, 0, 1);  // Yellow-Gold
        typeColor = new ColorRGBA(0.73f, 0.73f, 0, 1);  // Yellow-Gold
    }
    
    public float getCost(){
        return cost;
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
        if(data instanceof ModifierConduitData){
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
    public HashMap<String,Float> getSpellNodeProperties(){
        properties.put("Efficiency", Util.roundedFloat(multiplier, 2));
        return properties;
    }
    @Override
    public HashMap<String,Float> genProperties(int level){
        properties = new HashMap();
        cost = ItemFactory.leveledRandomFloat(-5f, level, 2);
        properties.put("Cost", cost);
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
    
    public void modifyCore(CoreVals vals){
        vals.m_effectMult *= effectMult;
    }
    
    @Override
    public void update(float tpf){
        // Not Needed
    }
}
