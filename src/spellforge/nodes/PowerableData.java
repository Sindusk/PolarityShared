package spellforge.nodes;

import java.util.ArrayList;

/**
 *
 * @author SinisteRing
 */
public class PowerableData extends SpellNodeData {
    protected ArrayList<GeneratorData> sources = new ArrayList();
    protected float cost = 0;
    
    public PowerableData(){}
    public PowerableData(SpellNodeData data){
        super(data.getX(), data.getY(), data.getLocation());
    }
    
    public ArrayList<GeneratorData> getSources(){
        return sources;
    }
    public float getCost(){
        return cost;
    }
    
    public void addPowerSource(GeneratorData data){
        if(!sources.contains(data)){
            sources.add(data);
        }
    }
}
