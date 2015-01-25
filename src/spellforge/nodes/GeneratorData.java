package spellforge.nodes;

import spellforge.nodes.conduits.PowerConduitData;
import com.jme3.network.serializing.Serializable;
import java.util.ArrayList;
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
    
    public GeneratorData(){}    // For serialization
    public GeneratorData(SpellNodeData data){
        super(data.getX(), data.getY(), data.getLocation());
    }
    
    @Override
    public String getTooltip(){
        float roundedPower = Math.round(storedPower*100f);
        roundedPower /= 100;
        return super.getTooltip()+"\n\nStored Power: "+roundedPower;
    }
    @Override
    public String getText(){
        return ""+Math.round(storedPower*100f)/100f;
    }
    
    @Override
    public boolean canProvide(SpellNodeData data){
        if(data instanceof CoreData){
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
    public void recalculate(SpellMatrix matrix){
        PulseHandler handler = new PulseHandler(matrix, this);
        handler.createPulse(this);
        granted = handler.getGranted();
    }
    
    @Override
    public void update(float tpf){
        storedPower += rate*tpf;
        if(storedPower > maxPower){
            storedPower = maxPower;
        }
    }
}
