package polarity.shared.netdata;

import com.jme3.math.Vector2f;
import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;

/**
 * Network data packet to play a sound at a given location.
 * @author Sindusk
 */
@Serializable
public class SoundLocationalData extends AbstractMessage {
    private int id;
    private Vector2f loc;
    private String sound;
    public SoundLocationalData() {}
    public SoundLocationalData(int id, Vector2f loc, String sound){
        this.id = id;
        this.loc = loc;
        this.sound = sound;
        setReliable(false);
    }
    public int getID(){
        return id;
    }
    public Vector2f getLoc(){
        return loc;
    }
    public String getSound(){
        return sound;
    }
}
