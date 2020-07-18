package network;

import com.jme3.network.Message;

/**
 *
 * @author SinisteRing
 */
public abstract class GameNetwork {
    public static final float MOVE_INTERVAL = 0.05f;
    public static final float MOVE_INVERSE = 1.0f/MOVE_INTERVAL;

    public abstract void send(Message m);
    public abstract void stop();
}
