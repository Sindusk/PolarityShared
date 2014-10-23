package action;

import character.CharacterManager;
import character.GameCharacter;
import com.jme3.math.Vector2f;
import com.jme3.network.serializing.Serializable;
import netdata.ProjectileData;

/**
 * Action which causes a Projectile to spawn. Within this class holds all the data required for proper
 * execution of the Projectile when its event (collision or otherwise) is called.
 * @author Sindusk
 */
@Serializable
public class ProjectileAttack extends Action {
    protected Event event;
    protected float speed;
    
    public ProjectileAttack(GameCharacter owner, Vector2f start, Vector2f target, float speed, boolean down){
        super(owner, start, target);
        this.speed = speed;
        this.target = target;
    }
    public ProjectileAttack(CharacterManager characterManager, ProjectileData data){
        super(characterManager.getPlayer(data.getOwner()), data.getStart(), data.getTarget());
        this.event = data.getEvent();
        this.speed = data.getSpeed();
    }
    
    public Event getEvent(){
        return event;
    }
    public Vector2f getStart(){
        return owner.getLocation();
    }
    public Vector2f getTarget(){
        return target;
    }
    public float getSpeed(){
        return speed;
    }
    
    public void setEvent(Event e){
        this.event = e;
    }
}
