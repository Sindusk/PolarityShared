package screens;

import com.jme3.app.Application;
import com.jme3.math.Vector2f;
import com.jme3.scene.Node;
import input.ClientBinding;
import input.InputHandler;
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
    
    public InventoryScreen(Application app, Node rootNode, Node guiNode){
        super(app, rootNode, guiNode);
        name="Inventory Screen";
    }
    
    @Override
    public void initialize(InputHandler inputHandler) {
        Util.log("Initialize: "+this.getName());
        invFrame = new Inventory(gui, new Vector2f(Sys.width*0.5f, Sys.height*0.5f), 600, 600, 1);
        invFrame.setTitle("Inventory");
        invFrame.addTab("all", "all");
        invFrame.addTab("weapons", "weapons");
        invFrame.addTab("ammo", "ammo");
        invFrame.addTab("tools", "tools");
        invFrame.addTab("crafting", "crafting");
        invFrame.addTab("alchemy", "alchemy");
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
        if(bind.equals(ClientBinding.Exit.toString())){
            Sys.getInputHandler().switchScreens(new MenuScreen(app, root.getParent(), gui.getParent()));
        }
    }
}
