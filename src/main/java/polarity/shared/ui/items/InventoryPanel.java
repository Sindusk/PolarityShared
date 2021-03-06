package polarity.shared.ui.items;

import com.jme3.math.Vector2f;
import com.jme3.scene.Node;
import polarity.shared.items.Inventory;
import polarity.shared.items.data.ItemData;
import java.util.ArrayList;
import polarity.shared.tools.Util;
import polarity.shared.ui.Panel;
import polarity.shared.ui.UIElement;

/**
 *
 * @author SinisteRing
 */
public class InventoryPanel extends Panel {
    protected Inventory inventory;
    protected int itemsPerRow = 10;
    
    public InventoryPanel(Node parent, Vector2f loc, float x, float y, float z){
        super(parent, loc, x, y, z);
    }
    public Inventory getInventory(){
        return inventory;
    }
    public void setInventory(Inventory inventory){
        this.inventory = inventory;
    }
    public void setItemsPerRow(int itemsPerRow){
        this.itemsPerRow = itemsPerRow;
    }
    
    public void display(){
        clear();
        ItemButton itemButton;
        float spacing = sizeX/itemsPerRow;
        float size = sizeX/itemsPerRow*0.8f;
        int num = 0;
        float x;
        float y = (sizeY*0.5f)-(spacing*0.5f);
        for(ItemData item : inventory.getList()){
            x = (spacing*num)-(sizeX*0.5f)+(spacing*0.5f);
            if(item == null){
                Util.log("ERROR!");
            }
            itemButton = new ItemButton(node, inventory, item, new Vector2f(x, y), size, size, 0){
                @Override
                public void onAction(Vector2f cursorLoc, String bind, boolean down, float tpf){
                    Util.log("onAction for ItemButton in an InventoryPanel", 2);
                }
            };
            addControl(itemButton);
            num++;
            if(num >= itemsPerRow){
                y -= spacing;
                num = 0;
            }
        }
    }
    public void clear(){
        ArrayList<UIElement> removals = new ArrayList();
        for(UIElement e : controls){
            if(e instanceof ItemButton){
                removals.add(e);
                e.destroy();
            }
        }
        for(UIElement e : removals){
            controls.remove(e);
        }
    }
}
