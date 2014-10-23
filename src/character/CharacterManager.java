package character;

import com.jme3.network.HostedConnection;
import com.jme3.scene.Node;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import main.GameServer;
import netdata.DamageData;
import netdata.MoveData;
import netdata.PlayerData;
import tools.Util;

/**
 * PlayerManager - Used for the creation and management of networked players.
 * @author SinisteRing
 */
public class CharacterManager{
    private Node node = new Node("CharacterNode");
    private HashMap<Integer,Integer> playerID = new HashMap();
    private Set<Integer> playerIDSet = playerID.keySet();
    private ArrayList<Player> players = new ArrayList();
    private int myID = -1;
    
    public CharacterManager(){
        Util.log("[CharacterManager] <Instance> Creating...", 1);
    }
    
    public Node getNode(){
        return node;
    }
    public Player getPlayer(int index){
        Util.log("[PlayerManager] <getPlayer> Getting player "+index, 4);
        return players.get(playerID.get(index));
    }
    public ArrayList<Player> getPlayers(){
        return players;
    }
    
    public void setMyID(int id){
        this.myID = id;
    }
    
    /**
     * Sends all player data to the passed in connection.
     * @param conn The connection of the player that will recieve the data.
     */
    public void sendData(HostedConnection conn){
        int i = 0;
        while(i < players.size()){
            if(players.get(i).isConnected() && players.get(i).getConnection() != conn){
                conn.send(players.get(i).getData());
            }
            i++;
        }
    }
    
    public void damagePlayer(DamageData d){
        Util.log("[CharacterManager] <damagePlayer> Damaging player "+d.getID()+" for "+d.getValue(), 1);
        if(playerID.containsKey(d.getID())){
            players.get(playerID.get(d.getID())).damage(d.getValue());
        }
    }
    
    public void updatePlayerLocation(MoveData d){
        Util.log("[PlayerManager] <updatePlayerLocation> Updating player "+d.getID()+" location to "+d.getLocation().toString(), 4);
        Util.log("playerID = "+playerID.toString(), 4);
        if(playerID.containsKey(d.getID())){
            players.get(playerID.get(d.getID())).updateLocation(d.getLocation());
            players.get(playerID.get(d.getID())).updateRotation(d.getCursorLocation());
        }else{
            Util.log("[PlayerManager] <updatePlayerLocation> Key not found: "+d.getID(), 1);
        }
    }
    public void update(float tpf){
        for(Integer i : playerIDSet){
            if(i != myID){
                players.get(playerID.get(i)).update(tpf);
            }
        }
    }
    
    public int findEmptyID(){
        int i = 0;
        while(true){
            if(!playerID.containsKey(i)){
                return i;
            }
            i++;
        }
    }
    public void add(PlayerData d){
        int id = d.getID();
        Player p = new Player(node, d);
        p.create();
        players.add(p);
        playerID.put(id,players.indexOf(p));
    }
    public void remove(int id){
        players.get(playerID.get(id)).destroy();
    }
    public int remove(HostedConnection conn){
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
