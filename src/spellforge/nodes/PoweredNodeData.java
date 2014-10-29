package spellforge.nodes;

import com.jme3.network.serializing.Serializable;
import java.util.ArrayList;
import spellforge.PulseHandler;
import spellforge.SpellMatrix;

/**
 *
 * @author SinisteRing
 */
@Serializable
public class PoweredNodeData extends SpellNodeData {
    protected ArrayList<SpellNodeData> granted;
    protected float storedPower = 0;
    protected float maxPower = 10;
    
    public PoweredNodeData(){}  // For serialization
    public PoweredNodeData(SpellNodeData data){
        super(data.getX(), data.getY(), data.getLocation());
    }
    
    @Override
    public String getTooltip(){
        return super.getTooltip()+"\n\nStored Power: "+storedPower;
    }
    
    @Override
    public void recalculate(SpellMatrix matrix){
        PulseHandler handler = new PulseHandler(matrix, this);
        handler.createPulse(this);
        granted = handler.getGranted();
    }
    
    public void grantPower(float amount){
        storedPower += amount;
        if(storedPower > maxPower){
            storedPower = maxPower;
        }
    }
}
