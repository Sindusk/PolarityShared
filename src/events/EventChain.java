package events;

import com.jme3.math.Vector2f;
import com.jme3.network.Server;
import java.util.ArrayList;
import world.World;

/**
 *
 * @author SinisteRing
 */
public class EventChain {
    protected ArrayList<Event> chain = new ArrayList();
    protected float distance;
    protected float spread;
    protected boolean finished = false;
    
    public EventChain(){}
    public EventChain(Vector2f start, Vector2f target){
        distance = start.distance(target);
    }
    
    public boolean isFinished(){
        return finished;
    }
    
    public void execute(Server server, World world, float tpf){
        int i = 0;
        while(i < chain.size()){
            Event event = chain.get(i);
            event.addTime(tpf);
            if(event.shouldExecute()){
                event.resetStart();
                event.execute(server, world);
                chain.remove(i);
            }else{
                i++;
            }
        }
        if(chain.isEmpty()){
            finished = true;
        }
    }
    
    public void addEvents(ArrayList<Event> events){
        if(events != null){
            chain.addAll(events);
        }
    }
}
