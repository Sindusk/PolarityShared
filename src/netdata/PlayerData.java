package netdata;

import com.jme3.math.Vector2f;
import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;
import items.Equipment;
import stats.StatWithMax;

/**
 *
 * @author SinisteRing
 */
@Serializable
public class PlayerData extends AbstractMessage {
  private int id;           //Client ID
  private Vector2f loc;     // Message Data
  private Equipment equip;        // Stat
  public PlayerData() {}
  public PlayerData(int ID, Vector2f location, Equipment equipment){
      loc = location;
      id = ID;
      equip = equipment;
  }
  public int getID(){
      return id;
  }
  public Vector2f getLocation(){
      return loc;
  }
  public Equipment getEquipment(){
      return equip;
  }
}
