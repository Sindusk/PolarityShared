package ui.items;

import character.Player;
import com.jme3.math.Vector2f;
import com.jme3.scene.Node;
import items.Inventory;
import items.Item;
import java.util.ArrayList;
import tools.Util;
import ui.Panel;
import ui.UIElement;

/**
 *
 * @author SinisteRing
 */
public class InventoryPanel extends Panel {
    protected Inventory inventory;
    protected int itemsPerRow;
    
    public InventoryPanel(Node parent, Vector2f loc, float x, float y, float z, int itemsPerRow){
        super(parent, loc, x, y, z);
        this.itemsPerRow = itemsPerRow;
    }
    public void setInventory(Player p){
        this.inventory = p.getInventory();
    }
    
    public void display(){
        clear();
        ItemButton itemButton;
        float spacing = sizeX/itemsPerRow;
        float size = sizeX/itemsPerRow*0.8f;
        int num = 0;
        float x;
        float y = (sizeY*0.5f)-(spacing*0.5f);
        for(Item item : inventory.getList()){
            x = (spacing*num)-(sizeX*0.5f)+(spacing*0.5f);
            itemButton = new ItemButton(node, item, new Vector2f(x, y), size, size, 0){
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
