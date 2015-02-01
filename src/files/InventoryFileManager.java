package files;

import items.Inventory;
import java.io.File;

/**
 *
 * @author SinisteRing
 */
public class InventoryFileManager extends FileManager {
    protected Inventory inventory;
    
    public InventoryFileManager(String filename){
        super(filename);
    }
    public void setInventory(Inventory inventory){
        this.inventory = inventory;
    }
    
    public void save(File file){
        //
    }
    public void load(File file){
        //
    }
}
