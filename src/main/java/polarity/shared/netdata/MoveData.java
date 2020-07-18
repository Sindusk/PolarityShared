package polarity.shared.netdata;

import com.jme3.math.Vector2f;
import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;

/**
 *
 * @author SinisteRing
 */
@Serializable
public class MoveData extends AbstractMessage {
  private int id;           // Client ID
  private Vector2f cLoc;    // Cursor Location
  private Vector2f loc;     // Entity Location
  public MoveData() {}
  public MoveData(int ID, Vector2f location, Vector2f cursorLoc){
      loc = location;
      cLoc = cursorLoc;
      id = ID;
      setReliable(false);
  }
  public int getID(){
      return id;
  }
  public Vector2f getLocation(){
      return loc;
  }
  public Vector2f getCursorLocation(){
      return cLoc;
  }
}
