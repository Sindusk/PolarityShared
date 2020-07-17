package character;

import character.data.MonsterData;
import com.jme3.network.HostedConnection;
import java.util.ArrayList;
import java.util.HashMap;
import netdata.DamageData;
import netdata.updates.MatrixUpdate;
import netdata.MoveData;
import character.data.PlayerData;
import character.types.CharType;
import character.types.Owner;
import netdata.HealData;
import tools.Util;
import world.World;

/**
 * PlayerManager - Used for the creation and management of networked players.
 * @author SinisteRing
 */
public class CharacterManager{
    private HashMap<Integer,Integer> playerID = new HashMap();
    private ArrayList<Player> players = new ArrayList();
    
    private HashMap<Integer,Integer> monsterID = new HashMap();
    private ArrayList<Monster> monsters = new ArrayList();
    
    private int myID = -1;
    
    public CharacterManager(){
        Util.log("[CharacterManager] <Constructor> Creating...", 1);
    }
    
    public Player getPlayer(int index){
        return players.get(playerID.get(index));
    }
    public ArrayList<Player> getPlayers(){
        return players;
    }
    public Monster getMonster(int index){
        return monsters.get(monsterID.get(index));
    }
    public ArrayList<Monster> getMonsters(){
        return monsters;
    }
    
    public void setMyID(int id){
        this.myID = id;
    }
    
    public GameCharacter getOwner(Owner owner){
        if(owner.getType() == CharType.PLAYER){
            return getPlayer(owner.getID());
        }else if(owner.getType() == CharType.MONSTER){
            return getMonster(owner.getID());
        }
        Util.log("[CharacterManager] <getOwner> Critical Error: Could not identify for CharType "+owner.getType().toString());
        return null;
    }
    
    /**
     * Sends all player data to the passed in connection.
     * @param conn The connection of the player that will recieve the data.
     */
    public void sendData(HostedConnection conn){
        for(Player player : players){
            if(player.isConnected() && player.getConnection() != conn){
                conn.send(player.getData());
            }
        }
        for(Monster monster : monsters){
            conn.send(monster.getData());
        }
    }
    
    public void heal(HealData d){
        if(d.getType() == CharType.MONSTER){
            getMonster(d.getID()).heal(d.getValue());
        }else if(d.getType() == CharType.PLAYER){
            getPlayer(d.getID()).heal(d.getValue());
        }else{
            Util.log("[CharacterManager] <damage> Critical Error: Could not identify EntityType for damage: "+d.getType().toString());
        }
    }
    public void damage(DamageData d){
        if(d.getType() == CharType.MONSTER){
            getMonster(d.getID()).damage(d.getValue());
        }else if(d.getType() == CharType.PLAYER){
            getPlayer(d.getID()).damage(d.getValue());
        }else{
            Util.log("[CharacterManager] <damage> Critical Error: Could not identify EntityType for damage: "+d.getType().toString());
        }
    }
    
    public void updateMatrix(MatrixUpdate d){
        players.get(playerID.get(d.getID())).updateMatrix(d);
    }
    public void updatePlayerLocation(MoveData d){
        if(playerID.containsKey(d.getID())){
            players.get(playerID.get(d.getID())).updateLocation(d.getLocation());
            players.get(playerID.get(d.getID())).updateRotation(d.getCursorLocation());
        }
    }
    public void serverUpdate(World world, float tpf){
        for(Player player : players){
            player.serverUpdate(tpf);
        }
        for(Monster monster : monsters){
            monster.serverUpdate(world, tpf);
        }
    }
    
    // Finds an empty ID slot for the given idList.
    public int findEmptyID(HashMap<Integer,Integer> idList, int limit){
        int i = 0;
        while(i < limit || limit == -1){
            if(!idList.containsKey(i)){
                return i;
            }
            i++;
        }
        return -1;
    }
    // TODO: Should be limited by server max players
    public int findEmptyPlayerID(){
        return findEmptyID(playerID, -1);
    }
    public int findEmptyMonsterID(){
        return findEmptyID(monsterID, -1);
    }
    public Player addPlayer(World world, PlayerData d){
        Player p = new Player(d);
        p.createEntity(world);
        players.add(p);
        playerID.put(d.getID(), players.indexOf(p));
        return p;
    }
    public Monster addMonster(World world, MonsterData d){
        Monster m = new Monster(d);
        m.createEntity(world);
        monsters.add(m);
        monsterID.put(d.getID(), monsters.indexOf(m));
        return m;
    }
    
    public void removePlayer(int id){
        players.get(playerID.get(id)).destroy();
    }
    public int removePlayer(HostedConnection conn){
        int i = 0;
        while(i < players.size()){
            if(players.get(i) != null && conn == players.get(i).getConnection()){
                players.get(i).destroy();
                return i;
            }
            i++;
        }
        return -1;
    }
}
