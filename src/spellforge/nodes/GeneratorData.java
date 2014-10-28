package spellforge.nodes;

import com.jme3.network.serializing.Serializable;

/**
 *
 * @author SinisteRing
 */
@Serializable
public class GeneratorData extends SpellNodeData {
    protected float rate;
    protected float efficiency;
    
    public GeneratorData(){}    // For serialization
    public GeneratorData(SpellNodeData data){
        super(data.getX(), data.getY(), data.getLocation());
    }
    
    @Override
    public boolean canConnect(SpellNodeData data){
        if(data instanceof PowerConduitData){
            return true;
        }
        return false;
    }
}
