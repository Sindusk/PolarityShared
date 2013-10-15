package screens;

import com.jme3.math.Vector2f;
import com.jme3.scene.Node;
import java.util.ArrayList;
import tools.T;
import ui.UIElement;

/**
 *
 * @author SinisteRing
 */
public abstract class Screen {
    protected Node gui = new Node("Screen GUI");
    protected Node root = new Node("Screen Root");
    protected ArrayList<UIElement> ui = new ArrayList();
    protected String name;
    
    // Default constructor
    public Screen(Node rootNode, Node guiNode){
        rootNode.attachChild(root);
        guiNode.attachChild(gui);
        name = "Default Screen";
    }
    
    // Getters
    public Node getNode(){
        return gui;
    }
    public String getName(){
        return name;
    }
    
    // Required methods to be implemented
    public abstract void initialize();
    public abstract boolean handleClick(Vector2f cursorLoc);
    public abstract boolean handleUnclick(Vector2f cursorLoc);
    
    // Basic destroy method
    public void destroy(){
        if(!root.removeFromParent()){
            T.log("Error 1: Could not detach screen root "+name);
        }
        if(!gui.removeFromParent()){
            T.log("Error 2: Could not detach screen gui "+name);
        }
    }
}
