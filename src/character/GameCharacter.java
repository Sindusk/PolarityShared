package character;

import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.network.serializing.Serializable;
import entity.Entity;

/**
 *
 * @author SinisteRing
 */
@Serializable
public abstract class GameCharacter {
    protected int id;
    protected Entity entity;
    
    public int getID(){
        return id;
    }
    public Entity getEntity(){
        return entity;
    }
    public Vector3f get3DLocation(){
        return new Vector3f(entity.getLocation().x, entity.getLocation().y, 0);
    }
    public Vector2f getLocation(){
        return entity.getLocation();
    }
    
    public void update(float tpf){
        entity.update(tpf);
    }
}
