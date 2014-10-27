package ui;

import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import java.util.ArrayList;

/**
 *
 * @author SinisteRing
 */
public class Menu {
    protected ArrayList<Button> options = new ArrayList();
    protected Node node = new Node("Menu");
    protected Vector2f loc;
    protected boolean active = false;
    
    public Menu(Node parent, Vector2f loc, float z){
        this.loc = loc;
        node.setLocalTranslation(new Vector3f(loc.x, loc.y, z));
        parent.attachChild(node);
        active = true;
    }
    
    public Node getNode(){
        return node;
    }
    public ArrayList<Button> getOptions(){
        return options;
    }
    public int size(){
        return options.size();
    }
    public boolean isActive(){
        return active;
    }
    
    public Button addOption(ArrayList<UIElement> ui, Button button){
        options.add(button);
        ui.add(button);
        return button;
    }
    
    public void destroy(ArrayList<UIElement> ui){
        // Remove all buttons from the ui.
        for(Button b : options){
            ui.remove(b);
        }
        node.removeFromParent();
        active = false;
    }
}
