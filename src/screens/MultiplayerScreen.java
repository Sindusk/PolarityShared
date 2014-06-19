package screens;

import com.jme3.app.Application;
import com.jme3.input.event.KeyInputEvent;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import input.ClientBinding;
import input.InputHandler;
import tools.Sys;
import tools.Util;
import ui.Button;
import ui.UIElement;

/**
 *
 * @author SinisteRing
 */
public class MultiplayerScreen extends Screen {
    private Button localButton;
    private Button hamachiButton;
    private Button addServerButton;
    
    public MultiplayerScreen(Application app, Node rootNode, Node guiNode){
        super(app, rootNode, guiNode);
        name="Multiplayer Screen";
    }
    
    @Override
    public void initialize(final InputHandler inputHandler) {
        Util.log("[MultiplayerScreen] Initializing...", 1);
        Sys.getCamera().setLocation(new Vector3f(0, 0, 50));
        Sys.getCamera().lookAt(Vector3f.ZERO, Vector3f.UNIT_Y);
        float width = Sys.width;
        float height = Sys.height;
        
        // Localhost button:
        localButton = new Button(gui, new Vector2f(width*0.5f, height*0.6f), width*0.4f, height*0.05f, 0){
            @Override
            public void onAction(Vector2f cursorLoc, String bind, boolean down, float tpf){
                if(bind.equals(ClientBinding.LClick.toString()) && down){
                    clientNetwork.connect("127.0.0.1");
                }
            }
        };
        localButton.setColor(new ColorRGBA(0.8f, 0, 0.8f, 1));
        localButton.setText("Localhost");
        ui.add(localButton);
        
        // Hamachi button:
        hamachiButton = new Button(gui, new Vector2f(width*0.5f, height*0.5f), width*0.4f, height*0.05f, 0){
            @Override
            public void onAction(Vector2f cursorLoc, String bind, boolean down, float tpf){
                if(bind.equals(ClientBinding.LClick.toString()) && down){
                    clientNetwork.connect("25.183.100.124");
                }
            }
        };
        hamachiButton.setColor(new ColorRGBA(0.4f, 0.4f, 0.8f, 1));
        hamachiButton.setText("Hamachi");
        ui.add(hamachiButton);// Hamachi button:
        
        addServerButton = new Button(gui, "aluminumPlate", new Vector2f(width*0.35f, height*0.3f), width*0.2f, height*0.05f, 0){
            @Override
            public void onAction(Vector2f cursorLoc, String bind, boolean down, float tpf){
                if(bind.equals(ClientBinding.LClick.toString()) && down){
                    inputHandler.switchScreens(new ServerEntryScreen(app, root.getParent(), gui.getParent()));
                }
            }
        };
        //addServerButton.setColor(new ColorRGBA(0.4f, 0.4f, 0.8f, 1));
        addServerButton.setText("Add Server");
        addServerButton.setTextColor(new ColorRGBA(0.4f, 0, 0, 1));
        ui.add(addServerButton);
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
    
    @Override
    public void onKeyEvent(KeyInputEvent evt){
        // implement
    }
}
