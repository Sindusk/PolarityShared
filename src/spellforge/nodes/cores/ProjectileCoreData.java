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
    public ProjectileCoreData(){
        name = "Projectile Core";
    }
    public ProjectileCoreData(SpellNodeData data){
        super(data);
        name = "Projectile Core";
    }
    
    @Override
    public String getIcon(){
        return "projectile";
    }
}
