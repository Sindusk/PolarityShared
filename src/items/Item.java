package items;

import com.jme3.network.serializing.Serializable;
import stats.StatList;

/**
 *
 * @author SinisteRing
 */
@Serializable
public class Item {
    protected String icon;      // Icon (without path) to be used by the item.
    protected StatList stats;  // Stat table for tooltip & effects.
    
    public Item(){}
    
    // Default constructor.
    public Item(String icon){
        this.icon = icon;
    }
}
