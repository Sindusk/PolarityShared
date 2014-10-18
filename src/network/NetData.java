package network;

import items.Equipment;
import items.Item;
import items.Weapon;
import netdata.*;
import stats.Stat;
import stats.StatWithMax;

/**
 *
 * @author SinisteRing
 */
public enum NetData {
    // Netdata
    CommandData(CommandData.class),
    ConnectData(ConnectData.class),
    DamageData(DamageData.class),
    DisconnectData(DisconnectData.class),
    IDData(IDData.class),
    MoveData(MoveData.class),
    PingData(PingData.class),
    PlayerData(PlayerData.class),
    ProjectileData(ProjectileData.class),
    ServerStatusData(ServerStatusData.class),
    SoundData(SoundData.class),
    
    // Data properties
    Equipment(Equipment.class),
    Item(Item.class),
    ServerSettings(ServerSettings.class),
    ServerStatus(ServerStatus.class),
    Stat(Stat.class),
    StatWithMax(StatWithMax.class),
    Weapon(Weapon.class);
    
    public final Class c;
    NetData(Class c){
        this.c = c;
    }
}
