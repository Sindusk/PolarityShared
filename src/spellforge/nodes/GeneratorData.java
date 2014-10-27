package spellforge.nodes;

import com.jme3.math.Vector2f;
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
}
