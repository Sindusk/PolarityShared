package spellforge.nodes;

import com.jme3.network.serializing.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author SinisteRing
 */
@Serializable
public class CoreData extends PoweredNodeData {
    protected HashMap<ModifierData, Float> efficiency = new HashMap();
    protected ArrayList<ModifierData> mods = new ArrayList();
    
    public CoreData(){} // For serialization
    public CoreData(SpellNodeData data){
        super(data);
    }
    
    @Override
    public boolean canConnect(SpellNodeData data){
        if(data instanceof ConduitData){
            return true;
        }
        return false;
    }
    
    @Override
    public void update(float tpf){
        // Update in-game tooltips etc.
    }
    
    public void addModifier(ModifierData data, float mult){
        efficiency.put(data, mult);
        if(mods.contains(data)){
            return;
        }
        mods.add(data);
    }
}
