package ui.items;

import com.jme3.math.Vector2f;
import com.jme3.scene.Node;
import items.Equipment;
import java.util.ArrayList;
import ui.Panel;
import ui.UIElement;

/**
 *
 * @author Sindusk
 */
public class EquipmentPanel extends Panel {
    protected Equipment equipment;
    
    public EquipmentPanel(Node parent, Vector2f loc, float x, float y, float z){
        super(parent, loc, x, y, z);
    }
    
    public void setEquipment(Equipment equipment){
        this.equipment = equipment;
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
