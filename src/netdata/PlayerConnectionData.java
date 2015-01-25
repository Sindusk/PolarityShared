package netdata;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;

/**
 *
 * @author SinisteRing
 */
@Serializable
public class PlayerConnectionData extends AbstractMessage {
    private int id = -1;
    private PlayerData pd;
    public PlayerConnectionData() {}
    public PlayerConnectionData(int id, PlayerData pd){
        this.id = id;
        this.pd = pd;
        this.setReliable(true);
    }
    public int getID(){
        return id;
    }
    public PlayerData getPlayerData(){
        return pd;
    }
}
