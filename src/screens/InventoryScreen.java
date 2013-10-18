package screens;

import com.jme3.math.Vector2f;
import com.jme3.scene.Node;
import input.ClientInputHandler;
import tools.T;
import ui.Inventory;
import ui.UIElement;

/**
 *
 * @author SinisteRing
 */
public class InventoryScreen extends Screen {
    private Inventory invFrame;
    
    public InventoryScreen(Node rootNode, Node guiNode){
        super(rootNode, guiNode);
        name="Inventory Screen";
    }
    
    @Override
    public void initialize(ClientInputHandler inputHandler) {
        invFrame = new Inventory(gui, new Vector2f(500, 400), 300, 500, 1){
            @Override
            public void onAction(String bind, boolean down, float tpf){
                // Do nothing
            }
        };
        ui.add(invFrame);
        ui.add(invFrame.getHeader());
    }
    
    @Override
    public void update(Vector2f cursorLoc) {
        //
    }
    
    // Handles the data that is filtered from onAction
    private void actionUI(UIElement e, String bind, boolean down, float tpf){
        e.onAction(bind, down, tpf);
    }
    
    // Called when a key is pressed or released
    @Override
    public void onAction(Vector2f cursorLoc, String bind, boolean down, float tpf) {
        UIElement e = checkUI(cursorLoc);
        if(e != null){
            actionUI(e, bind, down, tpf);
        }
    }
}
