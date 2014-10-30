package network;

import action.Event;
import world.blocks.BlockData;
import items.Equipment;
import items.Item;
import items.Weapon;
import netdata.*;
import netdata.requests.SpellMatrixRequest;
import stats.Stat;
import stats.StatWithMax;
import world.Chunk;
import world.blocks.WallData;

/**
 *
 * @author SinisteRing
 */
public enum NetData {
    // Netdata
    ActionData(ActionData.class),
    ChunkData(ChunkData.class),
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
    
    // Requests
    SpellMatrixRequest(SpellMatrixRequest.class),
    
    // Data properties
    Equipment(Equipment.class),
    Event(Event.class),
    Item(Item.class),
    ServerSettings(ServerSettings.class),
    ServerStatus(ServerStatus.class),
    Stat(Stat.class),
    StatWithMax(StatWithMax.class),
    Weapon(Weapon.class),
    
    // World properties
    Chunk(Chunk.class),
    
    BlockData(BlockData.class),
    WallData(WallData.class);
    public final Class c;
    NetData(Class c){
        this.c = c;
    }
}
