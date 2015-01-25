/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

import files.properties.vars.ServerVar;
import com.jme3.network.serializing.Serializable;
import files.properties.ServerProperties;

/**
 *
 * @author SinisteRing
 */
@Serializable
public class ServerStatus {
    protected boolean serverPlayerData = true;
    protected int numPlayers = 0;
    protected int maxPlayers = 10;
    protected boolean password = false;
    
    public ServerStatus(){}
    
    public void loadSettings(ServerProperties settings){
        serverPlayerData = Boolean.parseBoolean(settings.getVar(ServerVar.ServerPlayerData.getVar()));
        maxPlayers = Integer.parseInt(settings.getVar(ServerVar.MaxPlayers.getVar()));
        password = !settings.getVar(ServerVar.Password.getVar()).equals("");
    }
}
