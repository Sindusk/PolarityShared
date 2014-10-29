package spellforge.nodes;

import com.jme3.network.serializing.Serializable;
import java.util.ArrayList;
import spellforge.PulseHandler;
import spellforge.SpellMatrix;
import tools.Util;

/**
 *
 * @author SinisteRing
 */
@Serializable
public class GeneratorData extends SpellNodeData {
    protected ArrayList<SpellNodeData> granted;
    protected float rate;
    protected float efficiency;
    
    public GeneratorData(){}    // For serialization
    public GeneratorData(SpellNodeData data){
        super(data.getX(), data.getY(), data.getLocation());
    }
    
    @Override
    public boolean canProvide(SpellNodeData data){
        if(data instanceof CoreData || data instanceof ModifierData){
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
    public void recalculate(SpellMatrix matrix){
        PulseHandler handler = new PulseHandler(matrix, this);
        handler.createPulse(this);
        granted = handler.getGranted();
    }
    
    @Override
    public void update(float tpf){
        PoweredNodeData poweredData;
        for(SpellNodeData data : granted){
            if(data instanceof PoweredNodeData){
                poweredData = (PoweredNodeData) data;
                poweredData.grantPower(tpf);
            }else{
                Util.log("Error: Somehow powered node data is not within Generator's granted list!");
            }
        }
    }
}
