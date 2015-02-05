package events;

import character.GameCharacter;
import com.jme3.math.Vector2f;
import com.jme3.network.Server;
import com.jme3.network.serializing.Serializable;
import java.util.ArrayList;
import main.GameApplication;
import tools.Util;

/**
 *
 * @author SinisteRing
 */
@Serializable
public class Event {
    protected ArrayList<Action> actions = new ArrayList();
    
    protected GameCharacter owner;
    protected Vector2f start;
    protected Vector2f target;
    
    public Event(GameCharacter owner, Vector2f start, Vector2f target){
        this.owner = owner;
        this.start = start;
        this.target = target;
    }
    
    public GameCharacter getOwner(){
        return owner;
    }
    public Vector2f getStart(){
        return start;
    }
    public Vector2f getTarget(){
        return target;
    }
    public ArrayList<Action> getActions(){
        return actions;
    }
    
    public void execute(Server server, GameApplication app){
        Util.log("[Event] <execute> Critical error: No override for execute()!");
    }
    
    public void addAction(Action action){
        actions.add(action);
    }
    public void addActions(ArrayList<Action> actions){
        this.actions.addAll(actions);
    }
}
