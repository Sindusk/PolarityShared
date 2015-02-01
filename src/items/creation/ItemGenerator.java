package items.creation;

import com.jme3.math.FastMath;
import items.Inventory;
import items.Item;
import items.SpellNodeItem;
import spellforge.nodes.SpellNodeData;
import tools.Util;

/**
 *
 * @author SinisteRing
 */
public class ItemGenerator {
    public static Item randomItem(Inventory inv, int itemLevel){
        Item item;
        //int archetype = FastMath.nextRandomInt(0, 1);
        if(true){   // Spell Node is selected
            SpellNodeTypes[] types = SpellNodeTypes.values();
            int maxRoll = 0;
            for(SpellNodeTypes type : types){
                maxRoll += type.getDropWeight();
            }
            SpellNodeData data;
            SpellNodeTypes dataType = null;
            int roll = FastMath.nextRandomInt(0, maxRoll);
            int current = 0;
            for(SpellNodeTypes type : types){
                current += type.getDropWeight();
                if(roll <= current){
                    dataType = type;
                    break;
                }
            }
            if(dataType == null){
                Util.log("[ItemGenerator] Critical Error: dataType was not set for Spell Node!");
            }
            try{
                data = (SpellNodeData) dataType.getTypeClass().newInstance();
            }catch(InstantiationException ex){
                Util.log(ex);
                return null;
            }catch(IllegalAccessException ex){
                Util.log(ex);
                return null;
            }
            if(data == null){
                Util.log("[ItemGenerator] Error: Spell Node data was null, returning no item.");
                return null;
            }
            item = new SpellNodeItem(inv, itemLevel, data);
        }
        return item;
    }
    
    public static float leveledRandomFloat(float base, int level, int spaces){
        float mult = 1+0.05f*(float)Math.sqrt(level);
        float val = base*mult;
        float range = level*0.01f;
        float min = val-(val*range);
        float max = val+(val*range);
        return Util.roundedRandFloat(min, max, spaces);
    }
}
