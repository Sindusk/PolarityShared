package spellforge.nodes.conduits;

import com.jme3.math.ColorRGBA;
import spellforge.nodes.ConduitData;
import com.jme3.network.serializing.Serializable;
import spellforge.nodes.CoreData;
import spellforge.nodes.GeneratorData;
import spellforge.nodes.SpellNodeData;

/**
 *
 * @author SinisteRing
 */
@Serializable
public class PowerConduitData extends ConduitData {
    public PowerConduitData(){
        super();
        init();
    } // For serialization
    public PowerConduitData(SpellNodeData data){
        super(data);
        init();
    }
    
    private void init(){
        name = "Power Conduit";
        //typeColor = new ColorRGBA(0.76f, 0.15f, 0.97f, 1);  // Purple
        typeColor = new ColorRGBA(0.58f, 0.11f, 0.73f, 1);  // Purple
    }
    
    @Override
    public String getIcon(){
        return "powerConduit";
    }
    
    @Override
    public boolean canConnect(SpellNodeData data){
        if(data instanceof GeneratorData || data instanceof CoreData || data instanceof PowerConduitData){
            return true;
        }
        return false;
    }
}
