package ui.items;

import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import items.Inventory;
import items.Item;
import items.ItemData;
import java.util.HashMap;
import tools.Util.Vector2i;
import ui.Button;
import ui.interfaces.Draggable;
import ui.interfaces.TooltipInfo;

/**
 *
 * @author SinisteRing
 */
public class ItemButton extends Button implements TooltipInfo, Draggable {
    protected Item item;
    
    public ItemButton(Node parent, Vector2f loc, float x, float y, float z){
        super(parent, loc, x, y, z);
        setColor(new ColorRGBA(FastMath.nextRandomFloat(), FastMath.nextRandomFloat(), FastMath.nextRandomFloat(), 1));
    }
    public ItemButton(Node parent, Inventory inv, ItemData itemData, Vector2f loc, float x, float y, float z){
        super(parent, itemData.getIcon(), loc, x, y, z);
        this.item = new Item(inv, itemData);
    }
    
    public Item getItem(){
        return item;
    }
    
    public void resetDragging(){
        node.setLocalTranslation(new Vector3f(loc.x, loc.y, localZ));
    }
    public HashMap<Vector2i,ColorRGBA> getColorMap(){
        return item.getData().getColorMap();
    }
    public String getTooltip(){
        return item.getData().getTooltip();
    }
}
