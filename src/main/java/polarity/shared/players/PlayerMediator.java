package polarity.shared.players;

import com.jme3.network.HostedConnection;
import polarity.shared.character.Player;
import polarity.shared.character.data.PlayerData;
import polarity.shared.netdata.MoveData;
import polarity.shared.netdata.updates.MatrixUpdate;
import polarity.shared.world.GameWorld;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Mediates Player information between client and server.
 * @author Sindusk
 */
public class PlayerMediator {
    protected HashMap<Integer,Integer> playerID = new HashMap<>();
    protected ArrayList<Player> players = new ArrayList<>();

    public PlayerMediator(){
        // Called from child classes.
    }

    public Player getPlayer(int index){
        return players.get(playerID.get(index));
    }
    public ArrayList<Player> getPlayers(){
        return players;
    }

    public void updateMatrix(MatrixUpdate d){
        players.get(playerID.get(d.getID())).updateMatrix(d);
    }

    public void updateLocation(MoveData d){
        if(playerID.containsKey(d.getID())){
            players.get(playerID.get(d.getID())).updateLocation(d.getLocation());
            players.get(playerID.get(d.getID())).updateRotation(d.getCursorLocation());
        }
    }

    public Player addPlayer(GameWorld world, PlayerData d){
        Player p = new Player(d);
        p.createEntity(world);
        players.add(p);
        playerID.put(d.getID(), players.indexOf(p));
        return p;
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
