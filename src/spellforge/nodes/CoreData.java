package spellforge.nodes;

import com.jme3.network.serializing.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import spellforge.PulseHandler;
import spellforge.SpellMatrix;

/**
 *
 * @author SinisteRing
 */
@Serializable
public class CoreData extends SpellNodeData {
    protected ArrayList<SpellNodeData> granted;
    protected HashMap<SpellNodeData, Float> efficiency = new HashMap();
    protected ArrayList<ModifierData> mods = new ArrayList();
    protected ArrayList<EffectData> effects = new ArrayList();
    
    public CoreData(){} // For serialization
    public CoreData(SpellNodeData data){
        super(data.getX(), data.getY(), data.getLocation());
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
        PulseHandler handler = new PulseHandler(matrix, this);
        handler.createPulse(this);
        granted = handler.getGranted();
    }
    
    @Override
    public void update(float tpf){
        // Update in-game tooltips etc.
    }
    
    public void addEffect(EffectData data, float mult){
        efficiency.put(data, mult);
        if(effects.contains(data)){
            return;
        }
        effects.add(data);
    }
    public void addModifier(ModifierData data, float mult){
        efficiency.put(data, mult);
        if(mods.contains(data)){
            return;
        }
        mods.add(data);
    }
}
