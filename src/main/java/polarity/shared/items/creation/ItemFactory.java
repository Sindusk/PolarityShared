package polarity.shared.items.creation;

import com.jme3.math.FastMath;
import polarity.shared.items.Inventory;
import polarity.shared.items.data.ItemData;
import polarity.shared.items.data.SpellNodeItemData;
import polarity.shared.spellforge.nodes.SpellNodeData;
import polarity.shared.tools.Util;

/**
 *
 * @author SinisteRing
 */
public class ItemFactory {
    public static ItemData randomItem(Inventory inv, int itemLevel){
        ItemData item;
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
            item = new SpellNodeItemData(inv, itemLevel, data);
        }
        return item;
    }
    
    private static final float LEVEL_BASE_MULT = 0.04f;     // Changes the baseline value by level
    private static final float LEVEL_RANGE_MULT = 0.005f;   // Changes the spread of values by level
    private static final float MAX_INVERSE_PERC = 0.9f;     // Maximum % of baseline to reduce the baseline by when inverse
    private static final float MIN_BASE_MULT = 0.8f;        // Minimum % of baseline for min value
    private static final float MIN_RANGE_MULT = 0.25f;      // Adjusts the min value by level
    private static final float MAX_BASE_MULT = 1.2f;        // Minimum % of baseline for max value
    private static final float MAX_RANGE_MULT = 1.5f;       // Adjusts the max value by level
    
    public static float leveledRandomFloat(float base, int level, int spaces){
        float mult;
        if(base > 0){
            mult = 1+LEVEL_BASE_MULT*(float)Math.sqrt(level);
        }else if(base < 0){
            mult = 1-Math.min(LEVEL_BASE_MULT*(float)Math.sqrt(level), MAX_INVERSE_PERC);
        }else{
            Util.log("[ItemGenerator] Error: Base is zero.");
            return 0;
        }
        float newBase = Math.abs(base)*mult;
        float range = level*LEVEL_RANGE_MULT;
        float min = (newBase*MIN_BASE_MULT)*(1+(MIN_RANGE_MULT*range));
        float max = (newBase*MAX_BASE_MULT)*(1+(MAX_RANGE_MULT*range));
        return Util.roundedScaledRandFloat(min, max, spaces);
    }
    public static float leveledRandomPoweredFloat(float base, float power, int level, int spaces){
        float value = leveledRandomFloat(base, level, spaces);
        float diff = value - base;
        float multedDiff = diff * power;
        float newValue = value + multedDiff;
        return Util.roundedFloat(newValue, spaces);
    }
}
