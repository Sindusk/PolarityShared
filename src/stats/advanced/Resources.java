package stats.advanced;

import java.util.ArrayList;
import spellforge.nodes.GeneratorData;
import stats.Energy;
import stats.Mana;
import stats.Resource;

/**
 * Contains the actual values of the character's resources.
 * @author Sindusk
 */
public class Resources {
    protected Energy energy;
    protected Mana mana;
    
    public Resources(){
        energy = new Energy(50, 50);
        mana = new Mana(300, 300);
    }
    
    public Energy getEnergy(){
        return energy;
    }
    public Mana getMana(){
        return mana;
    }
    
    public void powerGenerators(Resource resource, ArrayList<GeneratorData> gens, float tpf){
        float cost = 0;
        for(GeneratorData gen : gens){
            cost += gen.getChargeCost(tpf);
        }
        float mult;
        if(cost <= resource.value()){
            mult = 1;
            resource.reduce(cost);
        }else{
            mult = resource.value()/cost;
            resource.reduce(cost);
        }
        for(GeneratorData gen : gens){
            gen.addPower(tpf*mult);
        }
    }
    
    public void update(float tpf){
        energy.add(10*tpf);
        mana.add(10*tpf*((mana.value()+(mana.getBase()*0.5f))/mana.getMax()));   // Scales based on current mana (more for higher)
    }
}
