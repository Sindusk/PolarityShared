package spellforge.nodes.conduits;

import com.jme3.math.ColorRGBA;
import spellforge.nodes.ConduitData;
import com.jme3.network.serializing.Serializable;
import spellforge.nodes.CoreData;
import spellforge.nodes.ModifierData;
import spellforge.nodes.SpellNodeData;

/**
 *
 * @author SinisteRing
 */
@Serializable
public class ModifierConduitData extends ConduitData {
    public ModifierConduitData(){
        super();
        init();
    }
    public ModifierConduitData(SpellNodeData data){
        super(data);
        init();
    }
    private void init(){
        name = "Modifier Conduit";
        //typeColor = new ColorRGBA(0.93f, 0.92f, 0, 1);  // Yellow-Gold
        typeColor = new ColorRGBA(0.73f, 0.73f, 0, 1);  // Yellow-Gold
    }
    
    @Override
    public String getIcon(){
        return "modifierConduit";
    }
    
    @Override
    public boolean canConnect(SpellNodeData data){
        if(data instanceof CoreData || data instanceof ModifierData || data instanceof ModifierConduitData){
            return true;
        }
        return false;
    }
}
