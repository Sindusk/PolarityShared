package spellforge.nodes;

import com.jme3.network.serializing.Serializable;
import spellforge.SpellMatrix;

/**
 *
 * @author SinisteRing
 */
@Serializable
public class ModifierData extends PoweredNodeData {
    protected float multiplier = 0;
    
    public ModifierData(){} // For serialization
    public ModifierData(SpellNodeData data){
        super(data);
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
    public void recalculate(SpellMatrix matrix){
        super.recalculate(matrix);
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
