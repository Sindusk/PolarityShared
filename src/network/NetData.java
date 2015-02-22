package network;

import spellforge.nodes.CoreVals;
import character.data.MonsterData;
import character.data.PlayerData;
import character.types.*;
import events.Action;
import items.Equipment;
import items.Inventory;
import items.ItemData;
import items.SpellNodeItemData;
import items.Weapon;
import netdata.*;
import netdata.destroyers.*;
import netdata.requests.*;
import netdata.responses.*;
import netdata.testing.*;
import netdata.updates.*;
import spellforge.nodes.SpellNodeData;
import spellforge.nodes.conduits.*;
import spellforge.nodes.cores.*;
import spellforge.nodes.effect.*;
import spellforge.nodes.generators.*;
import spellforge.nodes.modifiers.*;
import stats.Stat;
import stats.StatWithMax;
import tools.Vector2i;
import world.blocks.*;

/**
 *
 * @author SinisteRing
 */
public enum NetData {
    // Netdata
    ActionData(ActionData.class),
    CommandData(CommandData.class),
    ConnectData(ConnectData.class),
    DamageData(DamageData.class),
    DisconnectData(DisconnectData.class),
    MoveData(MoveData.class),
    PingData(PingData.class),
    PlayerData(PlayerData.class),
    PlayerConnectionData(PlayerConnectionData.class),
    PlayerIDData(PlayerIDData.class),
    ProjectileData(ProjectileData.class),
    ServerStatusData(ServerStatusData.class),
    SoundData(SoundData.class),
    
    // General Characters
    Owner(Owner.class),
    
    // Monsters
    MonsterData(MonsterData.class),
    MonsterStateUpdate(MonsterStateUpdate.class),
    
    // Item system
    Equipment(Equipment.class),
    Inventory(Inventory.class),
    ItemData(ItemData.class),
    SpellNodeItem(SpellNodeItemData.class),
    Weapon(Weapon.class),
    
    // Matrix Updates
    CoreVals(CoreVals.class),
    GeneratorPowerUpdate(GeneratorPowerUpdate.class),
    MatrixUpdate(MatrixUpdate.class),
    
    // - Spell Nodes -
    SpellNodeData(SpellNodeData.class), // Empty
    // Conduits
    EffectConduitData(EffectConduitData.class),
    ModifierConduitData(ModifierConduitData.class),
    PowerConduitData(PowerConduitData.class),
    // Cores
    ProjectileCoreData(ProjectileCoreData.class),
    // Effects
    DamageEffectData(DamageEffectData.class),
    PoisonEffectData(PoisonEffectData.class),
    // Generators
    EnergyGenData(EnergyGenData.class),
    // Modifiers
    MultiModData(MultiModData.class),
    SpeedModData(SpeedModData.class),
    
    // Data properties
    Event(Action.class),
    ServerStatus(ServerStatus.class),
    Stat(Stat.class),
    StatWithMax(StatWithMax.class),
    
    // Requests
    ChunkRequest(ChunkRequest.class),
    InventoryRequest(InventoryRequest.class),
    SpellMatrixRequest(SpellMatrixRequest.class),
    
    // Responses
    InventoryResponse(InventoryResponse.class),
    
    // Destroyers
    DestroyProjectileData(DestroyProjectileData.class),
    
    // World Blocks
    BlockData(BlockData.class),
    ChunkData(ChunkData.class),
    TerrainBlockData(TerrainData.class),
    WallData(WallData.class),
    
    // Tools/Utility
    Vector2i(Vector2i.class),
    
    // Testing
    DevLogData(DevLogData.class),
    MobCreateData(MonsterCreateData.class);
    
    public final Class c;
    NetData(Class c){
        this.c = c;
    }
}
