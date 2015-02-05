package spellforge.nodes.cores;

import character.GameCharacter;
import com.jme3.math.Vector2f;
import com.jme3.network.serializing.Serializable;
import events.Event;
import events.ProjectileEvent;
import items.creation.ItemGenerator;
import java.util.HashMap;
import spellforge.nodes.CoreData;
import spellforge.nodes.SpellNodeData;

/**
 *
 * @author SinisteRing
 */
@Serializable
public class ProjectileCoreData extends CoreData{
    protected float speed = 10;
    
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
    
    @Override
    public Event getEvent(GameCharacter owner, Vector2f start, Vector2f target){
        return new ProjectileEvent(owner, start, target, speed);
    }
    
    @Override
    public HashMap<String,Float> genProperties(int level){
        super.genProperties(level);
        speed = ItemGenerator.leveledRandomFloat(10f, level, 2);
        properties.put("Speed", speed);
        return properties;
    }
}
