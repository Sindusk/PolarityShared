package items;
import java.util.ArrayList;
import stats.Stat;

/**
 *
 * @author SinisteRing
 */
public class Inventory {
    protected ArrayList<Item> items;
    // Default constructor.
    public Inventory(){
        items = new ArrayList();
    }
    // Constructor with prior ArrayList of items
    public Inventory(ArrayList<Item> items){
        this.items = items;
    }
    
    // Getters:
    public ArrayList<Item> getList(){
        return items;
    }
    
    // Add an item priority-wise.
    public void add(Item item){
        for(Item i : items){
            if(i.getClass().equals(item.getClass())){
                //i.add(item.getCurrent());
            }
        }
        items.add(item);
    }
}

