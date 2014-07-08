/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package screens;

import com.jme3.app.Application;
import com.jme3.input.event.KeyInputEvent;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.scene.Node;
import input.ClientBinding;
import input.InputHandler;
import static screens.Screen.app;
import tools.Sys;
import tools.Util;
import ui.Button;
import ui.UIElement;

/**
 *
 * @author SinisteRing
 */
public class ServerEntryScreen extends Screen {
    protected Button addButton;
    
    public ServerEntryScreen(Application app, Node rootNode, Node guiNode){
        super(app, rootNode, guiNode);
        name = "ServerEntryScreen";
    }

    @Override
    public void initialize(InputHandler inputHandler) {
        Util.log("[ServerEntryScreen] Initializing...", 1);
        float width = Sys.width;
        float height = Sys.height;
        
        addButton = new Button(gui, new Vector2f(width*0.4f, height*0.3f), width*0.3f, height*0.05f, 0){
            @Override
            public void onAction(Vector2f cursorLoc, String bind, boolean down, float tpf){
                if(bind.equals(ClientBinding.LClick.toString()) && down){
                    //
                }
            }
        };
        addButton.setColor(new ColorRGBA(0.5f, 0.5f, 0.5f, 1));
        addButton.setText("Add");
    }

    @Override
    public void update(float tpf) {
        //
    }

    @Override
    public void onCursorMove(Vector2f cursorLoc) {
        //
    }

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
    public void onKeyEvent(KeyInputEvent evt) {
        //
    }
    
}
