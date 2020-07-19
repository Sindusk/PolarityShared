package polarity.shared.netdata;

import com.jme3.network.serializing.Serializable;

/**
 * Holds the status for a given server.
 * It is serializable so the data can be sent easily between server and client to update status.
 * @author Sindusk
 */
@Serializable
public class ServerStatusData {
    protected boolean serverPlayerData = true;  // If true, the server will hold player data. When false, clients provide their own player data.
    protected int numPlayers = 0;   // Current number of players connected to the server.
    protected int maxPlayers = 10;  // Maximum players connected to the server.
    protected boolean hasPassword = false;  // Whether the server has a password or not.
    
    public ServerStatusData(){} // For serialization

    public boolean getServerPlayerData(){
        return serverPlayerData;
    }
    public int getNumPlayers(){
        return numPlayers;
    }
    public int getMaxPlayers(){
        return maxPlayers;
    }
    public boolean getHasPassword(){
        return hasPassword;
    }
    
    public void setServerPlayerData(boolean serverPlayerData){
        this.serverPlayerData = serverPlayerData;
    }
    public void setNumPlayers(int numPlayers){
        this.numPlayers = numPlayers;
    }
    public void setMaxPlayers(int maxPlayers){
        this.maxPlayers = maxPlayers;
    }
    public void setHasPassword(boolean hasPassword){
        this.hasPassword = hasPassword;
    }
}
