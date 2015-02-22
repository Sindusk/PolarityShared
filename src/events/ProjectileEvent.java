package events;

import character.CharacterManager;
import character.GameCharacter;
import com.jme3.math.Vector2f;
import com.jme3.network.Server;
import com.jme3.network.serializing.Serializable;
import entity.Projectile;
import netdata.ProjectileData;
import spellforge.nodes.CoreVals;
import world.World;

/**
 * Action which causes a Projectile to spawn. Within this class holds all the data required for proper
 * execution of the Projectile when its event (collision or otherwise) is called.
 * @author Sindusk
 */
@Serializable
public class ProjectileEvent extends Event {
    protected int hashCode;
    protected float speed;
    
    public ProjectileEvent(GameCharacter owner, Vector2f start, Vector2f target, CoreVals values){
        super(owner, start, target);
        this.speed = values.m_speed;
    }
    public ProjectileEvent(CharacterManager charManager, ProjectileData data){
        super(charManager.getOwner(data.getOwner()), data.getStart(), data.getTarget());
        this.hashCode = data.getHashCode();
        this.speed = data.getSpeed();
    }
    
    @Override
    public void execute(Server server, World world){
        Projectile p = world.addProjectile(this);
        server.broadcast(new ProjectileData(p.hashCode(), owner.asOwner(), start, target, speed));
    }
    
    public int getHashCode(){
        return hashCode;
    }
    public float getSpeed(){
        return speed;
    }
}
