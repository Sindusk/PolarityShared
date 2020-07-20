package polarity.shared.monsters;

import polarity.shared.character.Monster;
import polarity.shared.character.data.MonsterData;
import polarity.shared.world.GameWorld;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Mediates network data for monsters between client and server.
 * @author Sindusk
 */
public class MonsterMediator {
    private HashMap<Integer,Integer> monsterID = new HashMap<>();
    private ArrayList<Monster> monsters = new ArrayList<>();

    public MonsterMediator(){
        // Called from child classes.
    }

    public Monster getMonster(int index){
        return monsters.get(monsterID.get(index));
    }
    public ArrayList<Monster> getMonsters(){
        return monsters;
    }

    public Monster addMonster(GameWorld world, MonsterData d){
        Monster m = new Monster(d);
        m.createEntity(world);
        monsters.add(m);
        monsterID.put(d.getID(), monsters.indexOf(m));
        return m;
    }
}
