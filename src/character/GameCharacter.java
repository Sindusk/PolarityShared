package character;

import character.types.Owner;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import entity.Entity;

/**
 *
 * @author SinisteRing
 */
public abstract class GameCharacter {
    protected int id;
    protected Entity entity;
    
    public GameCharacter(int id){
        this.id = id;
    }
    
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
    
    public Owner asOwner(){
        return new Owner(id, entity.getType());
    }
    
    public abstract boolean isEnemy(GameCharacter other);
    
    public void update(float tpf){
        entity.update(tpf);
    }
}
