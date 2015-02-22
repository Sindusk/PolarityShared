package spellforge.nodes.cores;

import character.GameCharacter;
import com.jme3.math.Vector2f;
import com.jme3.network.serializing.Serializable;
import events.Event;
import events.ProjectileEvent;
import spellforge.nodes.CoreData;
import spellforge.nodes.SpellNodeData;

/**
 *
 * @author SinisteRing
 */
@Serializable
public class ProjectileCoreData extends CoreData{
    public ProjectileCoreData(){
        init();
    }
    public ProjectileCoreData(SpellNodeData data){
        super(data);
        init();
    }
    private void init(){
        name = "Projectile Core";
    }
    
    @Override
    public String getIcon(){
        return "projectile";
    }
    
    @Override
    public Event getEvent(GameCharacter owner, Vector2f start, Vector2f target){
        return new ProjectileEvent(owner, start, target, values);
    }
}
