package polarity.shared.ui.items;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import polarity.shared.items.Inventory;
import polarity.shared.items.Item;
import polarity.shared.items.data.ItemData;
import java.util.HashMap;
import polarity.shared.tools.GeoFactory;
import polarity.shared.tools.SinText;
import polarity.shared.tools.Util;
import polarity.shared.tools.Vector2i;
import polarity.shared.ui.Button;
import polarity.shared.ui.interfaces.Draggable;
import polarity.shared.ui.interfaces.TooltipInfo;

/**
 *
 * @author SinisteRing
 */
public class ItemButton extends Button implements TooltipInfo, Draggable {
    protected Item item;
    protected SinText levelDisplay;
    
    public ItemButton(Node parent, Inventory inv, ItemData itemData, Vector2f loc, float x, float y, float z){
        super(parent, Util.getItemIcon(itemData.getIcon()), loc, x, y, z);
        this.item = new Item(inv, itemData);
        levelDisplay = GeoFactory.createSinText(node, y*0.2f, new Vector3f(x*0.5f, y*0.4f, 2), "ES32", ColorRGBA.White, SinText.Alignment.Right);
        levelDisplay.setText(itemData.getItemLevel()+"");
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
