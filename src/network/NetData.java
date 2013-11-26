/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

import netdata.*;

/**
 *
 * @author SinisteRing
 */
public enum NetData {
    CommandData(CommandData.class),
    ConnectData(ConnectData.class),
    DisconnectData(DisconnectData.class),
    IDData(IDData.class),
    MoveData(MoveData.class),
    PingData(PingData.class),
    PlayerData(PlayerData.class),
    SoundData(SoundData.class);
    
    public final Class c;
    NetData(Class c){
        this.c = c;
    }
}
