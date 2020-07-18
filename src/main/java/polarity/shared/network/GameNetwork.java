package polarity.shared.network;

import com.jme3.network.Message;

/**
 *
 * @author Sindusk
 */
public abstract class GameNetwork {
    public static final float MOVE_INTERVAL = 0.05f;
    public static final float MOVE_INVERSE = 1.0f/MOVE_INTERVAL;
    public boolean client = true;

    public abstract void send(Message m);
    public abstract void stop();
    public boolean isClient(){
        return client;
    }
}
