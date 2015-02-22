package events;

import character.GameCharacter;
import com.jme3.math.Vector2f;
import com.jme3.network.Server;
import com.jme3.network.serializing.Serializable;
import java.util.ArrayList;
import tools.Util;
import world.World;

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
    protected float timer = 0;
    protected float executeTime;
    
    public Event(GameCharacter owner, Vector2f start, Vector2f target){
        this.owner = owner;
        this.start = start.clone();
        this.target = target.clone();
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
    
    public void setExecuteTime(float executeTime){
        this.executeTime = executeTime;
    }
    
    public boolean shouldExecute(){
        return (timer >= executeTime);
    }
    
    // Test function for multiplier modifier fix
    public void resetStart(){
        start = owner.getLocation().clone();
    }
    
    public void offset(float spread){
        target.addLocal(Util.randFloat(-spread, spread), Util.randFloat(-spread, spread));
    }
    
    public void execute(Server server, World world){
        Util.log("[Event] <execute> Critical error: No override for execute()!");
    }
    
    public void addTime(float tpf){
        timer += tpf;
    }
    public void addAction(Action action){
        actions.add(action);
    }
    public void addActions(ArrayList<Action> actions){
        this.actions.addAll(actions);
    }
}
