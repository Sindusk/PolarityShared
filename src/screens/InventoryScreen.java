package screens;

import com.jme3.input.event.KeyInputEvent;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.scene.Node;
import input.Bind;
import input.InputHandler;
import main.GameApplication;
import tools.Sys;
import tools.Util;
import ui.Button;
import ui.FrameWithTabs;
import ui.UIElement;

/**
 *
 * @author SinisteRing
 */
public class InventoryScreen extends Screen {
    private FrameWithTabs invFrame;
    
    public InventoryScreen(GameApplication app, Node rootNode, Node guiNode){
        super(app, rootNode, guiNode);
        name="Inventory Screen";
    }
    
    @Override
    public void initialize(InputHandler inputHandler) {
        Util.log("Initialize: "+this.getName());
        invFrame = new FrameWithTabs(gui, new Vector2f(Sys.width*0.5f, Sys.height*0.5f), 600, 600, 1);
        invFrame.setTitle("Inventory");
        invFrame.addTab("all", "all");
        Button test = new Button(invFrame.getPanel("all").getNode(), new Vector2f(10, 10), 50, 50, 1){
            @Override
            public void onAction(Vector2f cursorLoc, String bind, boolean down, float tpf){
                if(bind.equals(Bind.LClick.toString()) && down){
                    Util.log("This is the item");
                }
            }
        };
        test.setColor(ColorRGBA.Orange);
        invFrame.getPanel("all").addControl(test);
        invFrame.getPanel("all").setColor(ColorRGBA.Black);
        invFrame.addTab("weapons", "weapons");
        invFrame.addTab("ammo", "ammo");
        invFrame.addTab("tools", "tools");
        invFrame.addTab("crafting", "crafting");
        invFrame.addTab("alchemy", "alchemy");
        ui.add(invFrame);
    }
    
    @Override
    public void changeInit(){
        //
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
        if(bind.equals(Bind.Escape.toString())){
            Sys.getInputHandler().switchScreens(new MenuScreen(app, root.getParent(), gui.getParent()));
        }
    }
    
    @Override
    public void onKeyEvent(KeyInputEvent evt){
        // implement
    }
}
