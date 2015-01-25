package netdata;

import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;
import items.Equipment;

/**
 *
 * @author SinisteRing
 */
@Serializable
public class PlayerData extends AbstractMessage {
  private int id;           // Server-side ID number for proper reference
  private String name;      // Player name
  private Vector2f loc;     // Location to spawn the player in
  private Equipment equip;  // Equipment the player is currently wearing (may need to be removed/changed)
  public PlayerData() {}
  public PlayerData(int id, String name, Vector2f loc, Equipment equip){
      this.id = id;
      this.name = name;
      this.loc = loc;
      this.equip = equip;
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
  public Equipment getEquipment(){
      return equip;
  }
}
