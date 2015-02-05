package spellforge.nodes;

import com.jme3.math.ColorRGBA;
import com.jme3.network.serializing.Serializable;
import events.Action;
import spellforge.PulseHandler;
import spellforge.SpellMatrix;
import spellforge.nodes.conduits.EffectConduitData;
import tools.Util;

/**
 *
 * @author SinisteRing
 */
@Serializable
public class EffectData extends PowerableData {
    protected float multiplier = 0;
    
    public EffectData(){
        init();
    }
    public EffectData(SpellNodeData data){
        super(data);
        init();
    }
    private void init(){
        type = "Effect";
        typeColor = ColorRGBA.Orange;
    }
    
    public Action getAction(){
        Util.log("[EffectData] <getAction> Critical error: no override on getAction()!");
        return null;
    }
    
    @Override
    public String getIcon(){
        return "effect";
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
