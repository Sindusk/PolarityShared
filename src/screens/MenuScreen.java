package screens;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import input.ClientInputHandler;
import java.util.ArrayList;
import tools.S;
import ui.Button;
import ui.UIElement;

/**
 *
 * @author SinisteRing
 */
public class MenuScreen extends Screen {
    private Button gridButton;
    private Button gameButton;
    private Button invButton;
    
    public MenuScreen(Node rootNode, Node guiNode){
        super(rootNode, guiNode);
        name = "Menu Screen";
    }
    
    @Override
    public void initialize(final ClientInputHandler inputHandler){
        // Initialize camera facing and location
        S.getCamera().setLocation(new Vector3f(0, 0, 50));
        S.getCamera().lookAt(Vector3f.ZERO, Vector3f.UNIT_Y);
        
        // Generate testing objects for... testing...
        gameButton = new Button(gui, new Vector2f(500, 600), 400, 40, 0);
        gameButton.changeColor(ColorRGBA.Gray);
        gameButton.setText("Start Game");
        ui.add(gameButton);
        // Grid button
        gridButton = new Button(gui, new Vector2f(500, 500), 400, 40, 0);
        gridButton.changeColor(new ColorRGBA(0, 0.7f, 0, 1));
        gridButton.setText("Sphere Grid");
        ui.add(gridButton);
        invButton = new Button(gui, new Vector2f(500, 400), 400, 40, 0);
        invButton.changeColor(new ColorRGBA(1, 0.9f, 0, 1));
        invButton.setText("Inventory");
        ui.add(invButton);
    }
    
    private void actionUI(UIElement e){
        if(e.equals(gridButton)){
            //gridButton.onClick();
            S.getInputHandler().switchScreens(new GridScreen(root.getParent(), gui.getParent()));
        }else if(e.equals(gameButton)){
            S.getInputHandler().switchScreens(new GameScreen(root.getParent(), gui.getParent()));
        }else if(e.equals(invButton)){
            S.getInputHandler().switchScreens(new InventoryScreen(root.getParent(), gui.getParent()));
        }
    }
    
    // Called when the mouse is moved
    @Override
    public void update(Vector2f cursorLoc){
        //
    }
    
    // Called when a key is pressed or released
    @Override
    public void onAction(Vector2f cursorLoc, String bind, boolean down, float tpf){
        UIElement e = checkUI(cursorLoc);
        if(e != null){
            actionUI(e);
        }
    }
}
