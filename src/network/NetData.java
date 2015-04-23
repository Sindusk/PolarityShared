package network;

import spellforge.nodes.CoreVals;
import character.data.MonsterData;
import character.data.PlayerData;
import character.types.*;
import equipment.AccessoryData;
import equipment.ArmorData;
import events.Action;
import equipment.Equipment;
import items.Inventory;
import items.data.ItemData;
import items.data.SpellNodeItemData;
import equipment.WeaponData;
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
import status.Status;
import status.negative.Poison;
import status.negative.Slow;
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
    HealData(HealData.class),
    MoveData(MoveData.class),
    PingData(PingData.class),
    PlayerData(PlayerData.class),
    PlayerConnectionData(PlayerConnectionData.class),
    PlayerIDData(PlayerIDData.class),
    ProjectileData(ProjectileData.class),
    ServerStatusData(ServerStatusData.class),
    SoundData(SoundData.class),
    
    // Chat Messages
    ChatMessage(ChatMessage.class),
    
    // General Characters
    Owner(Owner.class),
    
    // Monsters
    MonsterData(MonsterData.class),
    MonsterStateUpdate(MonsterStateUpdate.class),
    
    // Equipment
    Equipment(Equipment.class),
    AccessoryData(AccessoryData.class),
    ArmorData(ArmorData.class),
    WeaponData(WeaponData.class),
    
    // Item system
    Inventory(Inventory.class),
    ItemData(ItemData.class),
    SpellNodeItem(SpellNodeItemData.class),
    
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
    SlowEffectData(SlowEffectData.class),
    VampEffectData(VampEffectData.class),
    // Generators
    EnergyGenData(EnergyGenData.class),
    ManaGenData(ManaGenData.class),
    // Modifiers
    MultiModData(MultiModData.class),
    RadiusModData(RadiusModData.class),
    SpeedModData(SpeedModData.class),
    
    // Status System
    StatusData(StatusData.class),
    Status(Status.class),
    Poison(Poison.class),
    Slow(Slow.class),
    
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
    DestroyStatusData(DestroyStatusData.class),
    
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
