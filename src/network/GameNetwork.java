package network;

import com.jme3.network.Message;

/**
 *
 * @author SinisteRing
 */
public abstract class GameNetwork {
    public abstract void send(Message m);
    public abstract void stop();
}
