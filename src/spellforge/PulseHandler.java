package spellforge;

import java.util.ArrayList;
import java.util.HashMap;
import spellforge.nodes.SpellNodeData;
import tools.Vector2i;

/**
 *
 * @author SinisteRing
 */
public class PulseHandler {
    protected HashMap<Vector2i, SpellNodeData> checked = new HashMap();
    protected ArrayList<Pulse> pulses = new ArrayList();
    protected ArrayList<SpellNodeData> granted = new ArrayList();
    protected SpellMatrix matrix;
    protected SpellNodeData origin;
    
    public PulseHandler(SpellMatrix matrix, SpellNodeData data){
        this.matrix = matrix;
        this.origin = data;
        checked.put(data.getIndex(), data);
    }
    
    public ArrayList<SpellNodeData> getGranted(){
        return granted;
    }
    public SpellNodeData getOrigin(){
        return origin;
    }
    public boolean isChecked(Vector2i index){
        if(checked.get(index) != null){
            return true;
        }
        return false;
    }
    
    public void createPulse(SpellNodeData data){
        boolean[] connected = data.getConnections();
        int x = data.getX();
        int y = data.getY()+1;
        if(connected[0]){
            pulses.add(new Pulse(this, matrix, x, y));
        }
        x = data.getX()+1;
        y = data.getY();
        if(connected[1]){
            pulses.add(new Pulse(this, matrix, x, y));
        }
        x = data.getX();
        y = data.getY()-1;
        if(connected[2]){
            pulses.add(new Pulse(this, matrix, x, y));
        }
        x = data.getX()-1;
        y = data.getY();
        if(connected[3]){
            pulses.add(new Pulse(this, matrix, x, y));
        }
    }
    
    public void addGranted(SpellNodeData data){
        granted.add(data);
    }
    public void setChecked(SpellNodeData data){
        checked.put(data.getIndex(), data);
    }
}
