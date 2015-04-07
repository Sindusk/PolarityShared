package spellforge.nodes;

import character.GameCharacter;
import character.Player;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.network.HostedConnection;
import com.jme3.network.serializing.Serializable;
import events.Action;
import events.Event;
import items.creation.ItemFactory;
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
public class CoreData extends SpellNodeData {
    protected ArrayList<GeneratorData> sources = new ArrayList();
    protected HashMap<SpellNodeData, Float> efficiency = new HashMap();
    protected ArrayList<EffectData> effects = new ArrayList();
    protected ArrayList<ModifierData> mods = new ArrayList();
    
    public CoreVals values = new CoreVals();
    protected float cost = 0;
    
    public CoreData(){
        init();
    }
    public CoreData(SpellNodeData data){
        super(data.getX(), data.getY(), data.getLocation());
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
    public ArrayList<GeneratorData> getSources(){
        return sources;
    }
    public float getTotalCost(){
        float actionCost = cost;
        for(EffectData effect : effects){
            actionCost += effect.getCost();
        }
        for(ModifierData mod : mods){
            actionCost += mod.getCost();
        }
        return actionCost;
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
        values.speed = ItemFactory.leveledRandomPoweredFloat(7f, 0.3f, level, 2);
        properties.put("Speed", values.speed);
        cost = ItemFactory.leveledRandomFloat(-5f, level, 2);
        properties.put("Cost", cost);
        return properties;
    }
    
    @Override
    public void preRecalculate(){
        sources.clear();
        efficiency.clear();
        effects.clear();
        mods.clear();
    }
    @Override
    public void recalculate(SpellMatrix matrix){
        PulseHandler handler = new PulseHandler(matrix, this);
        handler.createPulse(this);
        granted = handler.getGranted();
    }
    
    public void addPowerSource(GeneratorData data){
        if(!sources.contains(data)){
            sources.add(data);
        }
    }
    
    public Event getEvent(GameCharacter owner, Vector2f start, Vector2f target){
        Util.log("[CoreData] <getEvent> Critical Error: No override for getEvent()!");
        return null;
    }
    
    public ArrayList<Action> calculateActions(HostedConnection conn, Player owner, ActionData data){
        float storedPower = 0;
        float actionCost = getTotalCost();
        // Determine the total amount of stored power:
        for(GeneratorData gen : sources){
            storedPower += gen.getStoredPower();
        }
        if(actionCost < storedPower){
            values.reset();
            ArrayList<Action> actions = new ArrayList();
            for(ModifierData mod : mods){
                mod.modifyCore(values);
            }
            for(EffectData effect : effects){
                actions.add(effect.getAction(values.m_effectMult));
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
    
    @Override
    public void update(float tpf){
        // Prevents error messages.
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
