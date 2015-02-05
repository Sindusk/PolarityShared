package events;

import character.CharacterManager;
import character.GameCharacter;
import com.jme3.math.Vector2f;
import com.jme3.network.Server;
import com.jme3.network.serializing.Serializable;
import entity.Projectile;
import main.GameApplication;
import netdata.ProjectileData;

/**
 * Action which causes a Projectile to spawn. Within this class holds all the data required for proper
 * execution of the Projectile when its event (collision or otherwise) is called.
 * @author Sindusk
 */
@Serializable
public class ProjectileEvent extends Event {
    protected int hashCode;
    protected float speed;
    
    public ProjectileEvent(GameCharacter owner, Vector2f start, Vector2f target, float speed){
        super(owner, start, target);
        this.speed = speed;
        this.start = start;
        this.target = target;
    }
    public ProjectileEvent(CharacterManager characterManager, ProjectileData data){
        super(characterManager.getPlayer(data.getOwner()), data.getStart(), data.getTarget());
        this.hashCode = data.getHashCode();
        this.speed = data.getSpeed();
    }
    
    @Override
    public void execute(Server server, GameApplication app){
        Projectile p = app.getWorld().addProjectile(this);
        server.broadcast(new ProjectileData(p.hashCode(), owner.getID(), start, target, speed));
    }
    
    public int getHashCode(){
        return hashCode;
    }
    public float getSpeed(){
        return speed;
    }
}
