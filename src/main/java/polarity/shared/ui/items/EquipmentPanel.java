package polarity.shared.ui.items;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import polarity.shared.equipment.Equipment;
import polarity.shared.equipment.EquipmentData;
import java.util.ArrayList;
import polarity.shared.tools.GeoFactory;
import polarity.shared.tools.Vector2i;
import polarity.shared.ui.Panel;
import polarity.shared.ui.UIElement;

/**
 *
 * @author Sindusk
 */
public class EquipmentPanel extends Panel {
    protected Equipment equipment;
    protected EquipmentButton[] weaponButton = new EquipmentButton[4];
    protected EquipmentButton[] accessoryButton = new EquipmentButton[2];
    protected EquipmentButton helmetButton;
    protected EquipmentButton bodyButton;
    protected EquipmentButton bootsButton;
    protected Geometry highlight;
    
    public EquipmentPanel(Node parent, Vector2f loc, float x, float y, float z){
        super(parent, loc, x, y, z);
    }
    
    public void setEquipment(Equipment equipment){
        this.equipment = equipment;
    }
    
    public int getWeaponIndex(EquipmentButton button){
        int i = 0;
        while(i < weaponButton.length){
            if(weaponButton[i] == button){
                return i;
            }
            i++;
        }
        return -1;
    }
    
    public void display(Vector2i highlightPos){
        clear();
        float size = Math.min(sizeY, sizeX*2)/10f;
        EquipmentData data;
        int i;
        
        highlight = GeoFactory.createBox(node, new Vector3f(size*0.55f, size*0.55f, 0), Vector3f.ZERO, ColorRGBA.Red);
        
        // Weapons
        i = 0;
        while(i < weaponButton.length){
            data = equipment.getWeapon(i);
            weaponButton[i] = new EquipmentButton(node, equipment, data, new Vector2f((i*size*1.5f)-size*2.25f, size*3), size, size, 1);
            controls.add(weaponButton[i]);
            if(highlightPos.x == 0 && highlightPos.y == i){
                highlight.setLocalTranslation(weaponButton[i].getLocation().subtract(0, 0, 0.01f));
            }
            i++;
        }
        
        // Accessories
        i = 0;
        while(i < accessoryButton.length){
            data = equipment.getAccessory(i);
            accessoryButton[i] = new EquipmentButton(node, equipment, data, new Vector2f((i*size*4f)-size*2f, size*1), size, size, 1);
            controls.add(accessoryButton[i]);
            i++;
        }
        
        // Armor
        data = equipment.getHelmet();
        helmetButton = new EquipmentButton(node, equipment, data, new Vector2f(0, size*1.5f), size, size, 1);
        controls.add(helmetButton);
        data = equipment.getBody();
        bodyButton = new EquipmentButton(node, equipment, data, new Vector2f(0, 0), size, size, 1);
        controls.add(bodyButton);
        data = equipment.getBoots();
        bootsButton = new EquipmentButton(node, equipment, data, new Vector2f(0, -size*1.5f), size, size, 1);
        controls.add(bootsButton);
    }
    public void clear(){
        ArrayList<UIElement> removals = new ArrayList();
        for(UIElement e : controls){
            if(e instanceof EquipmentButton){
                removals.add(e);
                e.destroy();
            }
        }
        for(UIElement e : removals){
            controls.remove(e);
        }
        if(highlight != null){
            highlight.removeFromParent();
        }
    }
}
