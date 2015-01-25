package items;

import com.jme3.network.serializing.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import tools.Util;

/**
 *
 * @author SinisteRing
 */
@Serializable
public abstract class Item {
    protected String icon;      // Icon (without path) to be used by the item.
    protected String archtype;  // Upper type, such as weapon, armor, etc.
    protected String type;      // Lower type, such as ranged, melee, head, etc.
    protected HashMap<String,Float> properties = new HashMap(); // Stat table for tooltip & effects.
    protected HashMap<String,ArrayList<Float>> flatMods = new HashMap();    // Flat modifiers
    protected HashMap<String,ArrayList<Float>> percentMods = new HashMap(); // Percent modifiers
    
    public Item(){}
    
    // Default constructor.
    public Item(String icon){
        this.icon = icon;
    }
    public String getArchtype(){
        return archtype;
    }
    public String getType(){
        return type;
    }
    
    public float getProperty(String name){
        return properties.get(name);
    }
    public void addProperty(String name, float value){
        properties.put(name, value);
    }
    
    public void removeFlatModifier(String name, float mod){
        if(flatMods.containsKey(name)){
            flatMods.get(name).remove(mod);
        }else{
            Util.log("Error: Tried removing nonexistant flat modifier.");
        }
    }
    public void addFlatModifier(String name, float mod){
        if(flatMods.containsKey(name)){
            flatMods.get(name).add(mod);
        }else{
            ArrayList<Float> arr = new ArrayList();
            arr.add(mod);
            flatMods.put(name, arr);
        }
    }
    
    public void removePercentModifier(String name, float mod){
        if(percentMods.containsKey(name)){
            percentMods.get(name).remove(mod);
        }else{
            Util.log("Error: Tried removing nonexistant percent modifier.");
        }
    }
    public void addPercentModifier(String name, float mod){
        if(percentMods.containsKey(name)){
            percentMods.get(name).add(mod);
        }else{
            ArrayList<Float> arr = new ArrayList();
            arr.add(mod);
            percentMods.put(name, arr);
        }
    }
    
    public float getValue(String name){
        float value = properties.get(name);
        if(flatMods.containsKey(name)){
            for(Float f : flatMods.get(name)){
                value += f;
            }
        }
        if(percentMods.containsKey(name)){
            for(Float f : percentMods.get(name)){
                value *= f;
            }
        }
        return value;
    }
}
