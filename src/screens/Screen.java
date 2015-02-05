package screens;

import com.jme3.input.event.KeyInputEvent;
import com.jme3.math.Vector2f;
import com.jme3.scene.Node;
import input.InputHandler;
import java.util.ArrayList;
import main.GameApplication;
import network.ClientNetwork;
import network.ServerNetwork;
import tools.Util;
import ui.UIElement;

/**
 *
 * @author SinisteRing
 */
public abstract class Screen {
    // Top-level variables
    protected static Node topRoot;
    protected static Node topGUI;
    protected static ClientNetwork clientNetwork;
    protected static ServerNetwork serverNetwork;
    
    // Class-level variables
    protected GameApplication app;
    protected InputHandler inputHandler;
    protected Node gui = new Node("Screen GUI");
    protected Node root = new Node("Screen Root");
    protected ArrayList<UIElement> ui = new ArrayList();
    protected String name;
    
    // Default constructor
    public Screen(GameApplication app, Node rootNode, Node guiNode){
        this.app = app;
        rootNode.attachChild(root);
        guiNode.attachChild(gui);
        name = "Default Screen";
    }
    
    // Getters
    public GameApplication getApp(){
        return app;
    }
    public InputHandler getInputHandler(){
        return inputHandler;
    }
    public String getName(){
        return name;
    }
    public void setVisible(boolean show){
        if(show){
            topRoot.attachChild(root);
            topGUI.attachChild(gui);
        }else{
            root.removeFromParent();
            gui.removeFromParent();
        }
    }
    
    // Returns the top-level root node. Parent of Screen-level root.
    public static Node getTopRoot(){
        return topRoot;
    }
    // Returns the top-level gui node. Parent of Screen-level gui.
    public static Node getTopGUI(){
        return topGUI;
    }
    
    public void setApplication(GameApplication app){
        this.app = app;
    }
    public static ClientNetwork getClientNetwork(){
        return Screen.clientNetwork;
    }
    public static void setClientNetwork(ClientNetwork clientNetwork){
        Screen.clientNetwork = clientNetwork;
    }
    public static void setNodes(Node topRoot, Node topGUI){
        Screen.topRoot = topRoot;
        Screen.topGUI = topGUI;
    }
    
    // Required methods to be implemented
    public abstract void initialize(final InputHandler inputHandler);
    public abstract void changeInit();
    public abstract void update(float tpf);
    public abstract void onCursorMove(Vector2f cursorLoc);
    public abstract void onAction(Vector2f cursorLoc, String bind, boolean down, float tpf);
    public abstract void onKeyEvent(KeyInputEvent evt);
    
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
            Util.log("Error: Could not detach screen root "+name);
        }
        if(!gui.removeFromParent()){
            Util.log("Error: Could not detach screen gui "+name);
        }
    }
}
