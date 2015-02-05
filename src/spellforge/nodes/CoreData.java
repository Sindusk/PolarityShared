package spellforge.nodes;

import character.GameCharacter;
import character.Player;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.network.HostedConnection;
import com.jme3.network.serializing.Serializable;
import events.Action;
import events.Event;
import items.creation.ItemGenerator;
import java.util.ArrayList;
import java.util.HashMap;
import netdata.ActionData;
import spellforge.PulseHandler;
import spellforge.SpellMatrix;
import tools.Util;

/**
 *
 * @author SinisteRing
 */
@Serializable
public class CoreData extends PowerableData {
    protected HashMap<SpellNodeData, Float> efficiency = new HashMap();
    protected ArrayList<EffectData> effects = new ArrayList();
    protected ArrayList<ModifierData> mods = new ArrayList();
    
    public CoreData(){
        init();
    }
    public CoreData(SpellNodeData data){
        super(data);
        init();
    }
    private void init(){
        type = "Core";
        typeColor = ColorRGBA.Cyan;
    }
    
    public ArrayList<EffectData> getEffects(){
        return effects;
    }
    public ArrayList<ModifierData> getModifiers(){
        return mods;
    }
    
    @Override
    public boolean canConnect(SpellNodeData data){
        if(data instanceof ConduitData){
            return true;
        }
        return false;
    }
    
    @Override
    public HashMap<String,Float> genProperties(int level){
        properties = new HashMap();
        cost = ItemGenerator.leveledRandomFloat(-5f, level, 2);
        properties.put("Cost", cost);
        return properties;
    }
    
    @Override
    public void recalculate(SpellMatrix matrix){
        PulseHandler handler = new PulseHandler(matrix, this);
        handler.createPulse(this);
        granted = handler.getGranted();
    }
    
    @Override
    public void update(float tpf){
        // Update in-game tooltips etc.
    }
    
    public Event getEvent(GameCharacter owner, Vector2f start, Vector2f target){
        Util.log("[CoreData] <getEvent> Critical Error: No override for getEvent()!");
        return null;
    }
    
    public ArrayList<Action> calculateActions(HostedConnection conn, Player owner, ActionData data){
        float storedPower = 0;
        float actionCost = cost;
        // Determine the total amount of stored power:
        for(GeneratorData gen : sources){
            storedPower += gen.getStoredPower();
        }
        for(EffectData effect : effects){
            actionCost += effect.getCost();
        }
        for(ModifierData mod : mods){
            actionCost += mod.getCost();
        }
        //
        if(actionCost < storedPower){
            ArrayList<Action> actions = new ArrayList();
            for(EffectData effect : effects){
                actions.add(effect.getAction());
            }
            float perc = actionCost / storedPower;
            for(GeneratorData gen : sources){
                gen.subtractPower(conn, data.getSlot(), gen.getStoredPower()*perc);
            }
            return actions;
        }else{
            //Util.log(conn, "Failed to use core at ["+getX()+","+getY()+"]: Not enough power.");
        }
        return null;
    }
    
    public void addEffect(EffectData data, float mult){
        efficiency.put(data, mult);
        if(effects.contains(data)){
            return;
        }
        effects.add(data);
    }
    public void addModifier(ModifierData data, float mult){
        efficiency.put(data, mult);
        if(mods.contains(data)){
            return;
        }
        mods.add(data);
    }
}
