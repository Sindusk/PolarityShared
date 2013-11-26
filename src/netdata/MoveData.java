package netdata;

import com.jme3.math.Vector2f;
import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;

/**
 *
 * @author SinisteRing
 */
@Serializable
public class MoveData extends AbstractMessage {
  private int id;           //Client ID
  private Vector2f loc;     // Message Data
  public MoveData() {}
  public MoveData(int ID, Vector2f location){
      loc = location;
      id = ID;
  }
  public int getID(){
      return id;
  }
  public Vector2f getLocation(){
      return loc;
  }
}
