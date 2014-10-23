package network;

import world.blocks.BlockData;
import com.jme3.scene.Geometry;
import items.Equipment;
import items.Item;
import items.Weapon;
import netdata.*;
import stats.Stat;
import stats.StatWithMax;
import world.Chunk;
import world.blocks.Block;
import world.blocks.WallData;

/**
 *
 * @author SinisteRing
 */
public enum NetData {
    // Netdata
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
    
    // Data properties
    Equipment(Equipment.class),
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
