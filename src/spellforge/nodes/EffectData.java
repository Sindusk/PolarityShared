package spellforge.nodes;

import com.jme3.network.serializing.Serializable;
import java.util.ArrayList;
import spellforge.PulseHandler;
import spellforge.SpellMatrix;
import spellforge.nodes.conduits.EffectConduitData;
import spellforge.nodes.conduits.ModifierConduitData;

/**
 *
 * @author SinisteRing
 */
@Serializable
public class EffectData extends SpellNodeData {
    protected ArrayList<SpellNodeData> granted;
    protected float multiplier = 0;
    
    public EffectData(){} // For serialization
    public EffectData(SpellNodeData data){
        super(data.getX(), data.getY(), data.getLocation());
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
        if(data instanceof EffectConduitData){
            return true;
        }
        return false;
    }
    
    @Override
    public String getText(){
        return Math.round(multiplier*100f)+"%";
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
                coreData.addEffect(this, multiplier);
            }
        }
    }
    
    @Override
    public void update(float tpf){
        // Not Needed
    }
}
