package screens;

import com.jme3.math.Vector2f;
import com.jme3.scene.Node;
import input.Binding;
import input.ClientInputHandler;
import tools.Sys;
import tools.Util;
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
        Util.log("Initialize: "+this.getName());
        invFrame = new Inventory(gui, new Vector2f(500, 400), 500, 700, 1);
        invFrame.setTitle("Inventory");
        ui.add(invFrame);
    }
    
    @Override
    public void update(float tpf){
        //
    }
    
    @Override
    public void onCursorMove(Vector2f cursorLoc) {
        //
    }
    
    // Called when a key is pressed or released
    @Override
    public void onAction(Vector2f cursorLoc, String bind, boolean down, float tpf) {
        UIElement e = checkUI(cursorLoc);
        if(e != null){
            e.onAction(cursorLoc, bind, down, tpf);
        }
        
        // Default bind back to menu screen
        if(bind.equals(Binding.Exit.toString())){
            Sys.getInputHandler().switchScreens(new MenuScreen(root.getParent(), gui.getParent()));
        }
    }
}
