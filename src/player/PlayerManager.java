package player;

import com.jme3.network.HostedConnection;
import com.jme3.scene.Node;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import netdata.MoveData;
import netdata.PlayerData;
import tools.Sys;
import tools.Util;

/**
 * PlayerManager - Used for the creation and management of networked players.
 * @author SinisteRing
 */
public class PlayerManager{
    private Node node = new Node("PlayerNode");
    private HashMap<Integer,Integer> playerID = new HashMap();
    private Set<Integer> playerIDSet = playerID.keySet();
    private ArrayList<Player> players = new ArrayList();
    private int myID = -1;
    
    public PlayerManager(){
        if(Sys.debug > 0){
            Util.log("[PlayerManager] <Instance> Creating...");
        }
    }
    
    public Node getNode(){
        return node;
    }
    public Player getPlayer(int index){
        if(Sys.debug > 4){
            Util.log("[PlayerManager] <getPlayer> Getting player "+index);
        }
        return players.get(playerID.get(index));
    }
    public ArrayList<Player> getPlayers(){
        return players;
    }
    
    public void setMyID(int id){
        this.myID = id;
    }
    
    public void sendData(HostedConnection conn){
        int i = 0;
        while(i < players.size()){
            if(players.get(i).isConnected() && players.get(i).getConnection() != conn){
                conn.send(players.get(i).getData());
            }
            i++;
        }
    }
    public void updatePlayerLocation(MoveData d){
        if(Sys.debug > 4){
            Util.log("[PlayerManager] <updatePlayerLocation> Updating player "+d.getID()+" location to "+d.getLocation().toString());
            Util.log("playerID = "+playerID.toString());
        }
        if(playerID.containsKey(d.getID())){
            players.get(playerID.get(d.getID())).updateLocation(d.getLocation());
            players.get(playerID.get(d.getID())).updateRotation(d.getCursorLocation());
        }else{
            if(Sys.debug > 1){
                Util.log("[PlayerManager] <updatePlayerLocation> Key not found: "+d.getID());
            }
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
        if(Sys.debug > 2){
            Util.log("[PlayerManager] <add> Adding new Player with ID: "+id);
        }
        Player p = new Player(node, d);
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
