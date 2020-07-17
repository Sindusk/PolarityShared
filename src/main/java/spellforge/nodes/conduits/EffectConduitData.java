package spellforge.nodes.conduits;

import com.jme3.math.ColorRGBA;
import spellforge.nodes.ConduitData;
import com.jme3.network.serializing.Serializable;
import spellforge.nodes.CoreData;
import spellforge.nodes.EffectData;
import spellforge.nodes.SpellNodeData;

/**
 *
 * @author SinisteRing
 */
@Serializable
public class EffectConduitData extends ConduitData {
    public EffectConduitData(){
        super();
        init();
    }
    public EffectConduitData(SpellNodeData data){
        super(data);
        init();
    }
    private void init(){
        name = "Effect Conduit";
        //typeColor = new ColorRGBA(0.22f, 0.80f, 0, 1);  // Green
        typeColor = new ColorRGBA(0.16f, 0.60f, 0, 1);  // Green
    }
    
    @Override
    public String getIcon(){
        return "effectConduit";
    }
    
    @Override
    public boolean canConnect(SpellNodeData data){
        if(data instanceof CoreData || data instanceof EffectData || data instanceof EffectConduitData){
            return true;
        }
        return false;
    }
}
