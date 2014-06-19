/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package screens;

import com.jme3.app.Application;
import com.jme3.input.event.KeyInputEvent;
import com.jme3.math.Vector2f;
import com.jme3.scene.Node;
import input.ClientBinding;
import input.InputHandler;
import static screens.Screen.app;
import tools.Sys;
import tools.Util;
import ui.UIElement;

/**
 *
 * @author SinisteRing
 */
public class ServerEntryScreen extends Screen {
    
    public ServerEntryScreen(Application app, Node rootNode, Node guiNode){
        super(app, rootNode, guiNode);
        name = "ServerEntryScreen";
    }

    @Override
    public void initialize(InputHandler inputHandler) {
        Util.log("[ServerEntryScreen] Initializing...", 1);
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
