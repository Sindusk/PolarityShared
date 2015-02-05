package network;

import events.Action;
import world.blocks.BlockData;
import items.Equipment;
import items.Inventory;
import items.ItemData;
import items.SpellNodeItemData;
import items.Weapon;
import netdata.*;
import netdata.destroyers.*;
import netdata.requests.*;
import netdata.responses.*;
import netdata.updates.*;
import spellforge.nodes.SpellNodeData;
import spellforge.nodes.conduits.*;
import spellforge.nodes.cores.*;
import spellforge.nodes.effect.*;
import spellforge.nodes.generators.*;
import spellforge.nodes.modifiers.*;
import stats.Stat;
import stats.StatWithMax;
import tools.Util.Vector2i;
import world.Chunk;
import world.blocks.ColorBlockData;
import world.blocks.IconBlockData;
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
    DevLogData(DevLogData.class),
    DisconnectData(DisconnectData.class),
    MoveData(MoveData.class),
    PingData(PingData.class),
    PlayerData(PlayerData.class),
    PlayerConnectionData(PlayerConnectionData.class),
    PlayerIDData(PlayerIDData.class),
    ProjectileData(ProjectileData.class),
    ServerStatusData(ServerStatusData.class),
    SoundData(SoundData.class),
    
    // Item system
    Equipment(Equipment.class),
    Inventory(Inventory.class),
    ItemData(ItemData.class),
    SpellNodeItem(SpellNodeItemData.class),
    Weapon(Weapon.class),
    
    // Matrix Updates
    GeneratorPowerUpdate(GeneratorPowerUpdate.class),
    MatrixUpdate(MatrixUpdate.class),
    
    // Spell Nodes
    DamageEffectData(DamageEffectData.class),
    EffectConduitData(EffectConduitData.class),
    EnergyGenData(EnergyGenData.class),
    ModifierConduitData(ModifierConduitData.class),
    MultiModData(MultiModData.class),
    PowerConduitData(PowerConduitData.class),
    ProjectileCoreData(ProjectileCoreData.class),
    SpeedModData(SpeedModData.class),
    SpellNodeData(SpellNodeData.class),
    
    // Data properties
    Event(Action.class),
    ServerStatus(ServerStatus.class),
    Stat(Stat.class),
    StatWithMax(StatWithMax.class),
    
    // Requests
    InventoryRequest(InventoryRequest.class),
    SpellMatrixRequest(SpellMatrixRequest.class),
    
    // Responses
    InventoryResponse(InventoryResponse.class),
    
    // Destroyers
    DestroyProjectileData(DestroyProjectileData.class),
    
    // World properties
    Chunk(Chunk.class),
    
    // World Blocks
    BlockData(BlockData.class),
    ColorBlockData(ColorBlockData.class),
    IconBlockData(IconBlockData.class),
    WallData(WallData.class),
    
    // Tools/Utility
    Vector2i(Vector2i.class);
    
    public final Class c;
    NetData(Class c){
        this.c = c;
    }
}
