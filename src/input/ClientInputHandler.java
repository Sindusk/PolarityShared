package input;

import com.jme3.input.InputManager;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.scene.Node;
import screens.Screen;

/**
 * ClientInputHandler - Handles all input from users and organizes them based on conditions.
 * @author SinisteRing
 */
public class ClientInputHandler implements ActionListener, AnalogListener{
    // Constant Variables:
    public static final float MOUSE_SENSITIVITY = 1;
    private final InputManager inputManager;
    private final Node guiNode;
    protected Screen screen;
    
    // Initialization
    public ClientInputHandler(InputManager inputManager, Node guiNode){
        this.guiNode = guiNode;
        this.inputManager = inputManager;
    }
    public void setupInputs(){
        for(Binding bind : Binding.values()){
            inputManager.addMapping(bind.mapping, bind.trigger);
            inputManager.addListener(this, bind.mapping);
        }
    }
    
    // Screen switching
    public void switchScreens(Screen newScreen){
        if(screen != null){
            screen.destroy();
        }
        screen = newScreen;
        screen.initialize(this);
    }
    
    // Action handlers
    public void onAction(String bind, boolean down, float tpf){
        if(screen == null){
            return;
        }
        screen.onAction(inputManager.getCursorPosition(), bind, down, tpf);
        /*if(down){ // When the key is pressed down
            if(bind.equals("LClick")){
                screen.handleClick(inputManager.getCursorPosition());
            }
        }else{ // When the key is released
            if(bind.equals("LClick")){
                screen.handleUnclick(inputManager.getCursorPosition());
            }
        }*/
    }
    public void onAnalog(String name, float value, float tpf){
        if(screen == null){
            return;
        }
        screen.update(inputManager.getCursorPosition());
    }
}
