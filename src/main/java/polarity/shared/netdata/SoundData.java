package polarity.shared.netdata;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;

/**
 *
 * @author SinisteRing
 */
@Serializable
public class SoundData extends AbstractMessage {
    private int id;
    private String sound;
    public SoundData() {}
    public SoundData(int id, String sound){
        this.id = id;
        this.sound = sound;
        setReliable(false);
    }
    public int getID(){
        return id;
    }
    public String getSound(){
        return sound;
    }
}
