package spellforge.nodes;

import com.jme3.math.ColorRGBA;
import com.jme3.network.serializing.Serializable;

/**
 *
 * @author SinisteRing
 */
@Serializable
public class ConduitData extends SpellNodeData {
    public ConduitData(){
        super();
        init();
    }  // For serialization
    public ConduitData(SpellNodeData data){
        super(data.getX(), data.getY(), data.getLocation());
        init();
    }
    
    private void init(){
        type = "Conduit";
        typeColor = ColorRGBA.Green;
    }
}
