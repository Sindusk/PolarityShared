package polarity.shared.items.data;

import com.jme3.math.ColorRGBA;
import com.jme3.network.serializing.Serializable;
import polarity.shared.items.Inventory;
import java.util.ArrayList;
import java.util.HashMap;
import polarity.shared.tools.Util;
import polarity.shared.tools.Vector2i;
import polarity.shared.ui.interfaces.TooltipInfo;

/**
 *
 * @author SinisteRing
 */
@Serializable
public abstract class ItemData implements TooltipInfo {
    protected static final ColorRGBA PROPERTY_NAME_COLOR = new ColorRGBA(0.8f, 0, 0.8f, 1);
    protected static final ColorRGBA PROPERTY_VALUE_COLOR = new ColorRGBA(0.9f, 0.4f, 0.9f, 1);
    
    protected HashMap<Vector2i,ColorRGBA> colorMap = new HashMap();         // Stores the map of colors for the tooltip
    protected HashMap<String,Float> properties = new HashMap();             // Stat table for tooltip & effects
    protected HashMap<String,ArrayList<Float>> flatMods = new HashMap();    // Flat modifiers
    protected HashMap<String,ArrayList<Float>> percentMods = new HashMap(); // Percent modifiers
    
    protected int itemLevel;    // Item level
    protected String name;      // Name of the item.
    protected String icon;      // Icon (without path) to be used by the item.
    protected String archetype; // Upper type, such as weapon, armor, etc.
    protected String type;      // Lower type, such as ranged, melee, helm, etc.
    
    protected ColorRGBA nameColor = new ColorRGBA(1, 0.8f, 0, 1);               // Tooltip color for name
    protected ColorRGBA archetypeColor = new ColorRGBA(0.5f, 0.5f, 0.5f, 1);    // Tooltip color for archetype
    protected ColorRGBA typeColor = new ColorRGBA(0.5f, 0.5f, 0.5f, 1);         // Tooltip color for type
    
    public ItemData(){}
    
    // Default constructor.
    public ItemData(Inventory inv, int itemLevel, String icon){
        this.itemLevel = itemLevel;
        this.name = "Random Item";
        this.icon = icon;
        this.archetype = "Unknown";
        this.type = "Unknown";
    }
    public int getItemLevel(){
        return itemLevel;
    }
    public String getIcon(){
        return icon;
    }
    public HashMap<Vector2i,ColorRGBA> getColorMap(){
        return colorMap;
    }
    public String getTooltip(){
        int start = 0;
        String info = name;
        colorMap.put(new Vector2i(start, info.length()), nameColor);
        
        start = info.length();
        info += " Lv"+itemLevel;
        colorMap.put(new Vector2i(start, info.length()), new ColorRGBA(1, 1, 1, 1));
        
        info += '\n';
        
        start = info.length();
        info += archetype;
        colorMap.put(new Vector2i(start, info.length()), archetypeColor);
        
        info += " - ";
        
        start = info.length();
        info += type;
        colorMap.put(new Vector2i(start, info.length()), typeColor);
        
        if(properties.keySet().isEmpty()){
            return info;
        }
        info += "\n";
        for(String key : properties.keySet()){
            info += "\n";
            start = info.length();
            info += key+": ";
            colorMap.put(new Vector2i(start, info.length()), PROPERTY_NAME_COLOR);
            
            start = info.length();
            info += properties.get(key);
            colorMap.put(new Vector2i(start, info.length()), PROPERTY_VALUE_COLOR);
        }
        return info;
    }
    public String getArchtype(){
        return archetype;
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
