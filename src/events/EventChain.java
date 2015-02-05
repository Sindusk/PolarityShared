package events;

import com.jme3.network.Server;
import java.util.ArrayList;
import main.GameApplication;

/**
 *
 * @author SinisteRing
 */
public class EventChain {
    protected ArrayList<Event> chain = new ArrayList();
    
    public EventChain(){}
    
    public void execute(Server server, GameApplication app){
        for(Event event : chain){
            event.execute(server, app);
        }
    }
    
    public void addEvents(ArrayList<Event> events){
        if(events != null){
            chain.addAll(events);
        }
    }
}
