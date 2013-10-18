package input;

import com.jme3.input.InputManager;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import screens.Screen;
import tools.T;
import ui.Frame;

/**
 * ClientInputHandler - Handles all input from users and organizes them based on conditions.
 * @author SinisteRing
 */
public class ClientInputHandler implements ActionListener, AnalogListener{
    // Constant Variables:
    public static final float MOUSE_SENSITIVITY = 1;
    private final InputManager inputManager;
    protected Screen screen;
    public Frame moving = null;
    
    // Initialization
    public ClientInputHandler(InputManager inputManager){
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
        if(bind.equals(Binding.LClick.toString()) && !down){
            moving = null;
        }
        screen.onAction(inputManager.getCursorPosition(), bind, down, tpf);
    }
    public void onAnalog(String name, float value, float tpf){
        if(screen == null){
            return;
        }
        value *= 1000f;
        if(moving != null){
            if(name.equals(Binding.MouseUp.toString())){
                moving.move(value, true);
            }else if(name.equals(Binding.MouseDown.toString())){
                moving.move(-value, true);
            }else if(name.equals(Binding.MouseRight.toString())){
                moving.move(value, false);
            }else if(name.equals(Binding.MouseLeft.toString())){
                moving.move(-value, false);
            }
        }
        screen.update(inputManager.getCursorPosition());
    }
}
