package character.data;

import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;

/**
 *
 * @author SinisteRing
 */
@Serializable
public class MonsterData extends AbstractMessage {
    protected int id;
    protected String name;
    protected Vector2f loc;
    public MonsterData(){}
    public MonsterData(int id, String name, Vector2f loc){
        this.id = id;
        this.name = name;
        this.loc = loc;
    }
    public int getID(){
        return id;
    }
    public String getName(){
        return name;
    }
  public Vector3f get3DLocation(){
      return new Vector3f(loc.x, loc.y, 0);
  }
    public Vector2f getLocation(){
        return loc;
    }
}
