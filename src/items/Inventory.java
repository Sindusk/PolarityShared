package items;

import com.jme3.math.FastMath;
import com.jme3.network.serializing.Serializable;
import java.util.ArrayList;
import tools.Util;

/**
 *
 * @author SinisteRing
 */
@Serializable
public class Inventory {
    protected ArrayList<ItemData> items;
    // Default constructor.
    public Inventory(){
        items = new ArrayList();
    }
    // Constructor with prior ArrayList of items
    public Inventory(ArrayList<ItemData> items){
        this.items = items;
    }
    
    // Getters:
    public ArrayList<ItemData> getList(){
        return items;
    }
    
    // Add an item priority-wise.
    public void add(ItemData item){
        items.add(item);
    }
    public void remove(ItemData item){
        items.remove(item);
    }
    public void removeRandom(){
        if(items.size() > 0){
            items.remove(FastMath.nextRandomInt(0, items.size()-1));
        }else{
            Util.log("Error: Inventory is empty, cannot remove item.");
        }
    }
}

