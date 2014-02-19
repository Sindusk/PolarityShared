package player;

import com.jme3.network.HostedConnection;
import com.jme3.scene.Node;
import java.util.ArrayList;
import java.util.HashMap;
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
    private ArrayList<Player> players = new ArrayList();
    
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
    
    public void sendData(HostedConnection conn){
        int i = 0;
        while(i < players.size()){
            if(players.get(i) != null && players.get(i).isConnected() && players.get(i).getConnection() != conn){
                conn.send(players.get(i).getData());
            }
            i++;
        }
    }
    public void updatePlayerLocation(MoveData d){
        if(players.get(playerID.get(d.getID())) != null){
            players.get(playerID.get(d.getID())).setLocation(d.getLocation());
        }
    }
    public void update(float tpf){
        int i = 0;
        while(i < players.size()){
            if(players.get(i) != null && players.get(i).isConnected()){
                players.get(i).update(tpf);
            }
            i++;
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
