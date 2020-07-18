package polarity.shared.files.properties;

import polarity.shared.character.Player;
import com.jme3.math.Vector2f;
import polarity.shared.files.PropertiesFileManager;
import polarity.shared.files.properties.vars.PlayerVar;
import polarity.shared.equipment.Equipment;
import polarity.shared.character.data.PlayerData;

/**
 *
 * @author SinisteRing
 */
public class PlayerProperties extends PropertiesFileManager {
    public PlayerProperties(String saveFilename){
        super(saveFilename);
        for(PlayerVar v : PlayerVar.values()){
            vars.put(v.getVar(), v.getValue());
        }
    }
    public PlayerData getPlayerData(int id){
        String name = getVar(PlayerVar.PlayerName.getVar()); // Get player name
        if(name.equals("")){
            name = "Player "+id;
        }
        float x = Float.parseFloat(getVar(PlayerVar.LocX.getVar()));
        float y = Float.parseFloat(getVar(PlayerVar.LocY.getVar()));
        return new PlayerData(id, name, new Vector2f(x, y), new Equipment());
    }
    public void savePlayerData(Player p){
        vars.put(PlayerVar.PlayerName.getVar(), p.getName());
        vars.put(PlayerVar.LocX.getVar(), String.valueOf(p.getLocation().x));
        vars.put(PlayerVar.LocY.getVar(), String.valueOf(p.getLocation().y));
        save();
    }
}
