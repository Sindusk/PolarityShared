package spellforge.nodes.cores;

import com.jme3.network.serializing.Serializable;
import spellforge.nodes.CoreData;
import spellforge.nodes.SpellNodeData;

/**
 *
 * @author SinisteRing
 */
@Serializable
public class ProjectileCoreData extends CoreData{
    public ProjectileCoreData(){}   // For serialization
    public ProjectileCoreData(SpellNodeData data){
        super(data);
        type = "Projectile Core";
    }
}
