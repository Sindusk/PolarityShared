package polarity.shared.network;

import polarity.shared.spellforge.nodes.CoreVals;
import polarity.shared.character.data.MonsterData;
import polarity.shared.character.data.PlayerData;
import polarity.shared.character.types.*;
import polarity.shared.equipment.AccessoryData;
import polarity.shared.equipment.ArmorData;
import polarity.shared.actions.Action;
import polarity.shared.equipment.Equipment;
import polarity.shared.items.Inventory;
import polarity.shared.items.data.ItemData;
import polarity.shared.items.data.SpellNodeItemData;
import polarity.shared.equipment.WeaponData;
import polarity.shared.netdata.*;
import polarity.shared.netdata.destroyers.*;
import polarity.shared.netdata.requests.*;
import polarity.shared.netdata.responses.*;
import polarity.shared.netdata.testing.*;
import polarity.shared.netdata.updates.*;
import polarity.shared.spellforge.nodes.SpellNodeData;
import polarity.shared.spellforge.nodes.conduits.*;
import polarity.shared.spellforge.nodes.cores.*;
import polarity.shared.spellforge.nodes.effect.*;
import polarity.shared.spellforge.nodes.generators.*;
import polarity.shared.spellforge.nodes.modifiers.*;
import polarity.shared.stats.Stat;
import polarity.shared.stats.StatWithMax;
import polarity.shared.status.Status;
import polarity.shared.status.negative.Poison;
import polarity.shared.status.negative.Slow;
import polarity.shared.tools.Vector2i;
import polarity.shared.world.blocks.*;

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
