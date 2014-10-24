package screens;

import com.jme3.input.event.KeyInputEvent;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import input.ClientBinding;
import input.InputHandler;
import main.GameApplication;
import tools.Sys;
import tools.Util;
import ui.Button;
import ui.UIElement;

/**
 *
 * @author SinisteRing
 */
public class MenuScreen extends Screen {
    private Button multiButton;
    private Button spellForgeButton;
    private Button invButton;
    
    public MenuScreen(GameApplication app, Node rootNode, Node guiNode){
        super(app, rootNode, guiNode);
        Util.log("[MenuScreen] Initializing New MenuScreen...", 3);
        name = "Menu Screen";
    }
    
    @Override
    public void initialize(final InputHandler inputHandler){
        // Initialize camera facing and location
        Sys.getCamera().setLocation(new Vector3f(0, 0, 50));
        Sys.getCamera().lookAt(Vector3f.ZERO, Vector3f.UNIT_Y);
        float width = Sys.width;
        float height = Sys.height;
        
        // Multiplayer button
        multiButton = new Button(gui, new Vector2f(width*0.5f, height*0.7f), width*0.4f, height*0.05f, 0){
            @Override
            public void onAction(Vector2f cursorLoc, String bind, boolean down, float tpf){
                if(bind.equals(ClientBinding.LClick.toString()) && down){
                    inputHandler.switchScreens(new MultiplayerScreen(app, root.getParent(), gui.getParent()));
                }
            }
        };
        multiButton.setColor(new ColorRGBA(0.7f, 0, 0.5f, 1));
        multiButton.setText("Multiplayer");
        ui.add(multiButton);
        
        // Inventory button
        spellForgeButton = new Button(gui, new Vector2f(width*0.5f, height*0.6f), width*0.4f, height*0.05f, 0){
            @Override
            public void onAction(Vector2f cursorLoc, String bind, boolean down, float tpf){
                if(bind.equals(ClientBinding.LClick.toString()) && down){
                    inputHandler.switchScreens(new SpellForgeScreen(app, root.getParent(), gui.getParent()));
                }
            }
        };
        spellForgeButton.setColor(new ColorRGBA(1, 0.3f, 0, 1));
        spellForgeButton.setText("Spell Forge");
        ui.add(spellForgeButton);
        
        // Inventory button
        invButton = new Button(gui, new Vector2f(width*0.5f, height*0.5f), width*0.4f, height*0.05f, 0){
            @Override
            public void onAction(Vector2f cursorLoc, String bind, boolean down, float tpf){
                if(bind.equals(ClientBinding.LClick.toString()) && down){
                    inputHandler.switchScreens(new InventoryScreen(app, root.getParent(), gui.getParent()));
                }
            }
        };
        invButton.setColor(new ColorRGBA(1, 0.9f, 0, 1));
        invButton.setText("Inventory");
        ui.add(invButton);
    }
    
    private void actionUI(UIElement e, Vector2f cursorLoc, String bind, boolean down, float tpf){
        e.onAction(cursorLoc, bind, down, tpf);
    }
    
    @Override
    public void update(float tpf){
        //
    }
    
    // Called when the mouse is moved
    @Override
    public void onCursorMove(Vector2f cursorLoc){
        //
    }
    
    // Called when a key is pressed or released
    @Override
    public void onAction(Vector2f cursorLoc, String bind, boolean down, float tpf){
        UIElement e = checkUI(cursorLoc);
        if(e != null){
            actionUI(e, cursorLoc, bind, down, tpf);
        }
    }
    
    @Override
    public void onKeyEvent(KeyInputEvent evt){
        // implement
    }
}
