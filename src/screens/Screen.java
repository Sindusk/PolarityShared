package screens;

import com.jme3.math.Vector2f;
import com.jme3.scene.Node;
import input.ClientInputHandler;
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
    public abstract void initialize(final ClientInputHandler inputHandler);
    public abstract void update(float tpf);
    public abstract void onCursorMove(Vector2f cursorLoc);
    public abstract void onAction(Vector2f cursorLoc, String bind, boolean down, float tpf);
    
    // Helper method for detecting UI collision
    protected UIElement checkUI(Vector2f cursorLoc){
        // Find all UI elements that are underneath the cursor location
        ArrayList<UIElement> results = new ArrayList();
        for(UIElement e : ui){
            if(e.withinBounds(cursorLoc)){
                results.add(e);
            }
        }
        // Parse the results and take an action according to how many there are
        if(results.size() > 0){
            // If one result, use it
            if(results.size() == 1){
                return results.get(0);
            }else{ // Otherwise, iterate through and find the one on top
                int i = 1;
                int result = 0;
                float current = results.get(0).getPriority();
                while(i < results.size()){
                    if(current < results.get(i).getPriority()){
                        result = i;
                    }
                    i++;
                }
                return results.get(result);
            }
        }
        return null;
    }
    
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
