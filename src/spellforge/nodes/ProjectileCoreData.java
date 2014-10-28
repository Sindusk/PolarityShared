package spellforge.nodes;

import com.jme3.network.serializing.Serializable;

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
