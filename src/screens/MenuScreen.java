/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package screens;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.scene.Node;
import java.util.ArrayList;
import tools.T;
import ui.Button;
import ui.Menu;
import ui.UIElement;

/**
 *
 * @author SinisteRing
 */
public class MenuScreen extends Screen {
    protected ArrayList<UIElement> elements = new ArrayList();
    
    public MenuScreen(Node guiNode){
        super(guiNode);
        name = "Menu Screen";
    }
    
    public void initialize(){
        Menu menu = new Menu(node, new Vector2f(300, 50), 200, 100, 0);
        elements.add(menu);
        Button button1 = new Button(node, new Vector2f(100, 50), 100, 100, 0);
        button1.changeColor(ColorRGBA.White);
        elements.add(button1);
        Button button2 = new Button(node, new Vector2f(50, 30), 80, 50, -1);
        button2.changeColor(ColorRGBA.Gray);
        elements.add(button2);
    }
    
    private void action(UIElement e){
        // do work
    }
    
    // Figure out what element of the screen was clicked
    public boolean handleClick(Vector2f cursorLoc){
        // Find all UI elements that are underneath the cursor location
        ArrayList<UIElement> results = new ArrayList();
        for(UIElement e : elements){
            if(e.withinBounds(cursorLoc)){
                results.add(e);
            }
        }
        // Parse the results and take an action according to how many there are
        if(results.size() > 0){
            // If one result, use it
            if(results.size() == 1){
                action(results.get(0));
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
                action(results.get(result));
            }
            return true;
        }
        return false;
    }
    
    public boolean handleUnclick(Vector2f cursorLoc){
        return false;
    }
}
