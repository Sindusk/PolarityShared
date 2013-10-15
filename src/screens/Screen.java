/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package screens;

import com.jme3.math.Vector2f;
import com.jme3.scene.Node;
import tools.T;

/**
 *
 * @author SinisteRing
 */
public abstract class Screen {
    protected Node node = new Node("Screen");
    protected String name;
    
    // Default constructor
    public Screen(Node guiNode){
        guiNode.attachChild(node);
        name = "Default Screen";
    }
    
    // Getters:
    public Node getNode(){
        return node;
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
        if(!node.removeFromParent()){
            T.log("Error 1: Could not detach screen "+name);
        }
    }
}
