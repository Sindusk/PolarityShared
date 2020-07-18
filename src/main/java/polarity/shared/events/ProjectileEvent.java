package polarity.shared.events;

import com.jme3.math.Vector2f;
import com.jme3.network.Server;
import com.jme3.network.serializing.Serializable;
import polarity.shared.character.CharacterManager;
import polarity.shared.character.GameCharacter;
import polarity.shared.entity.Projectile;
import polarity.shared.netdata.ProjectileData;
import polarity.shared.spellforge.nodes.CoreVals;
import polarity.shared.world.GameWorld;

/**
 * Event which causes a Projectile to spawn. Within this class holds all the data required for proper
 * execution of the Projectile when its event (collision or otherwise) is called.
 * @author Sindusk
 */
@Serializable
public class ProjectileEvent extends Event {
    protected int hashCode;
    protected float speed;
    
    public ProjectileEvent(GameCharacter owner, Vector2f start, Vector2f target, CoreVals values){
        super(owner, start, target, values);
        this.speed = values.m_speed;
    }
    public ProjectileEvent(CharacterManager charManager, ProjectileData data){
        super(charManager.getOwner(data.getOwner()), data.getStart(), data.getTarget(), data.getValues());
        this.hashCode = data.getHashCode();
        this.speed = values.m_speed;
    }

    /**
     * @param server Server instance to broadcast the projectile being added.
     * @param world ServerWorld object that should handle the addition of the projectile.
     */
    @Override
    public void execute(Server server, GameWorld world){
        Projectile p = world.addProjectile(this);
        server.broadcast(new ProjectileData(p.hashCode(), owner.asOwner(), start, target, values));
    }
    
    public int getHashCode(){
        return hashCode;
    }
    public float getSpeed(){
        return speed;
    }
}
