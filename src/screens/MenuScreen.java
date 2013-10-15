package screens;

import com.jme3.font.BitmapText;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import java.util.ArrayList;
import tools.CG;
import tools.S;
import tools.T;
import ui.Button;
import ui.UIElement;

/**
 *
 * @author SinisteRing
 */
public class MenuScreen extends Screen {
    private Button gridButton;
    
    public MenuScreen(Node rootNode, Node guiNode){
        super(rootNode, guiNode);
        name = "Menu Screen";
    }
    
    @Override
    public void initialize(){
        // Initialize camera facing and location
        S.getCamera().setLocation(new Vector3f(0, 0, 50));
        S.getCamera().lookAt(Vector3f.ZERO, Vector3f.UNIT_Y);
        
        // Generate testing objects for... testing...
        BitmapText text = CG.createText(gui, 20, new Vector3f(425, 530, 1), "OCRAStd", ColorRGBA.Orange);
        text.setText("Sphere Grid");
        gridButton = new Button(gui, new Vector2f(350, 500), 300, 40, 0);
        gridButton.changeColor(ColorRGBA.Blue);
        ui.add(gridButton);
        Button button2 = new Button(gui, new Vector2f(50, 30), 80, 50, -1);
        button2.changeColor(ColorRGBA.Gray);
        ui.add(button2);
    }
    
    private void action(UIElement e){
        if(e.equals(gridButton)){
            S.getInputHandler().switchScreens(new GridScreen(root.getParent(), gui.getParent()));
        }
    }
    
    // Called when the screen is clicked
    @Override
    public boolean handleClick(Vector2f cursorLoc){
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
    
    @Override
    public boolean handleUnclick(Vector2f cursorLoc){
        return false;
    }
}