package player;

import com.jme3.network.HostedConnection;
import com.jme3.scene.Node;
import netdata.MoveData;
import netdata.PlayerData;
import network.ServerNetwork;

/**
 * PlayerManager - Used for the creation and management of networked players.
 * @author SinisteRing
 */
public class PlayerManager{
    private Node node = new Node("PlayerNode");
    private Player[] player = new Player[16];
    
    public PlayerManager(){
        
    }
    
    public Node getNode(){
        return node;
    }
    public Player getPlayer(int index){
        return player[index];
    }
    public Player[] getPlayers(){
        return player;
    }
    
    public void sendData(HostedConnection conn){
        int i = 0;
        while(i < player.length){
            if(player[i] != null && player[i].isConnected() && player[i].getConnection() != conn){
                conn.send(player[i].getData());
            }
            i++;
        }
    }
    public void updatePlayerLocation(MoveData d){
        if(player[d.getID()] != null){
            player[d.getID()].setLocation(d.getLocation());
        }
    }
    public void update(float tpf){
        int i = 0;
        while(i < player.length){
            if(player[i] != null && player[i].isConnected()){
                player[i].update(tpf);
            }
            i++;
        }
    }
    
    public int findEmptyPlayer(){
        int i = 0;
        while(i < player.length){
            if(player[i] == null || !player[i].isConnected()){
                return i;
            }
            i++;
        }
        return -1;
    }
    public void add(PlayerData d){
        int id = d.getID();
        if(player[id] == null || !player[id].isConnected()){
            if(player[id] == null){
                player[id] = new Player();
            }
            player[id] = new Player(d);
        }
    }
    public void remove(int id){
        player[id].destroy();
    }
    public int remove(HostedConnection conn){
        int i = 0;
        while(i < player.length){
            if(player[i] != null && conn == player[i].getConnection()){
                player[i].destroy();
                return i;
            }
            i++;
        }
        return -1;
    }
}
