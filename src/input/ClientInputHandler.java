package input;

import com.jme3.input.Input;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseAxisTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.input.controls.Trigger;
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
    
    private enum Binding{
        MouseLeft(MouseInput.AXIS_X, true),
        MouseRight(MouseInput.AXIS_X, true),
        MouseUp(MouseInput.AXIS_Y, false),
        MouseDown(MouseInput.AXIS_Y, true),
        LClick(MouseInput.BUTTON_LEFT, MouseInput.class),
        RClick(MouseInput.BUTTON_RIGHT, MouseInput.class),
        Exit(KeyInput.KEY_ESCAPE, KeyInput.class);
        
        public final String mapping;
        public final int key;
        public final Trigger trigger;
        
        Binding(int key, boolean dir){
            mapping = this.toString();
            this.key = key;
            trigger = new MouseAxisTrigger(key, dir);
        }
        Binding(int key, Class <? extends Input> input){
            mapping = this.toString();
            this.key = key;
            if(input == MouseInput.class){
                trigger = new MouseButtonTrigger(key);
            }else{
                trigger = new KeyTrigger(key);
            }
        }
    }
    
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
        screen.initialize();
    }
    
    // Action handlers
    public void onAction(String bind, boolean down, float tpf){
        if(screen == null){
            return;
        }
        if(down){ // When the key is pressed down
            if(bind.equals("LClick")){
                screen.handleClick(inputManager.getCursorPosition());
            }
        }else{ // When the key is released
            if(bind.equals("LClick")){
                screen.handleUnclick(inputManager.getCursorPosition());
            }
        }
    }
    public void onAnalog(String name, float value, float tpf){
        // Placeholder
    }
}
