package polarity.shared.netdata;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;

/**
 *
 * @author SinisteRing
 */
@Serializable
public class CommandData extends AbstractMessage {
    private String command;
    public CommandData() {}
    public CommandData(String command){
        this.command = command;
    }
    public String getCommand(){
        return command;
    }
}
