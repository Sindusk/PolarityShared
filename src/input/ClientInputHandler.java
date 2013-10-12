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
import screens.MainScreen;

/**
 * ClientInputHandler - Handles all input from users and organizes them based on conditions.
 * @author SinisteRing
 */
public class ClientInputHandler implements ActionListener, AnalogListener{
    // Constant Variables:
    public static final float MOUSE_SENSITIVITY = 1;
    private final InputManager inputManager;
    
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
    
    public ClientInputHandler(InputManager inputManager){
        this.inputManager = inputManager;
    }
    
    public void setupInputs(){
        for(Binding bind : Binding.values()){
            inputManager.addMapping(bind.mapping, bind.trigger);
            inputManager.addListener(this, bind.mapping);
        }
    }
    
    public void onAction(String bind, boolean down, float tpf){
        if(!MainScreen.isActive()){
            return;
        }
        if(down){
            if(bind.equals("LClick")){
                MainScreen.handleClick();
            }
        }else{
            if(bind.equals("LClick")){
                MainScreen.handleUnclick();
            }
        }
    }
    public void onAnalog(String name, float value, float tpf){
        if(!MainScreen.isActive()){
            return;
        }
        int x=3;
        MainScreen.update();
    }
}
