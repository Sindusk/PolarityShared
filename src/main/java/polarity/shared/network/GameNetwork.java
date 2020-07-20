package polarity.shared.network;

import com.jme3.network.Message;
import polarity.shared.character.GameCharacter;
import polarity.shared.character.types.CharType;
import polarity.shared.character.types.Owner;
import polarity.shared.monsters.MonsterMediator;
import polarity.shared.netdata.DamageData;
import polarity.shared.netdata.HealData;
import polarity.shared.netdata.MoveData;
import polarity.shared.netdata.updates.MatrixUpdate;
import polarity.shared.players.PlayerMediator;
import polarity.shared.tools.Util;

/**
 *
 * @author Sindusk
 */
public abstract class GameNetwork {
    public static final float MOVE_INTERVAL = 0.05f;
    public static final float MOVE_INVERSE = 1.0f/MOVE_INTERVAL;
    protected PlayerMediator playerMediator;
    protected MonsterMediator monsterMediator;

    public GameNetwork(PlayerMediator playerMediator, MonsterMediator monsterMediator){
        this.playerMediator = playerMediator;
        this.monsterMediator = monsterMediator;
    }

    public abstract void send(Message m);
    public abstract void stop();

    /**
     * Gets the character that is represented by the given Owner object.
     * @param owner Owner object to get the GameCharacter for.
     * @return GameCharacter object for the given Owner.
     */
    public GameCharacter getOwner(Owner owner){
        if(owner.getType() == CharType.PLAYER){
            return playerMediator.getPlayer(owner.getID());
        }else if(owner.getType() == CharType.MONSTER){
            return monsterMediator.getMonster(owner.getID());
        }
        Util.log("[CharacterManager] <getOwner> Critical Error: Could not identify for CharType "+owner.getType().toString());
        return null;
    }

    public void heal(HealData d){
        if(d.getType() == CharType.MONSTER){
            monsterMediator.getMonster(d.getID()).heal(d.getValue());
        }else if(d.getType() == CharType.PLAYER){
            playerMediator.getPlayer(d.getID()).heal(d.getValue());
        }else{
            Util.log("[CharacterManager] <damage> Critical Error: Could not identify EntityType for damage: "+d.getType().toString());
        }
    }

    public void damage(DamageData d){
        if(d.getType() == CharType.MONSTER){
            monsterMediator.getMonster(d.getID()).damage(d.getValue());
        }else if(d.getType() == CharType.PLAYER){
            playerMediator.getPlayer(d.getID()).damage(d.getValue());
        }else{
            Util.log("[CharacterManager] <damage> Critical Error: Could not identify EntityType for damage: "+d.getType().toString());
        }
    }

    public void updatePlayerMatrix(MatrixUpdate d){
        playerMediator.updateMatrix(d);
    }

    public void updatePlayerLocation(MoveData d){
        playerMediator.updateLocation(d);
    }
}
