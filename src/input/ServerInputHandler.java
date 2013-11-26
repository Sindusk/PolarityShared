package input;

import com.jme3.input.InputManager;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;

/**
 * ClientInputHandler - Handles all input from users and organizes them based on conditions.
 * @author SinisteRing
 */
public class ServerInputHandler extends InputHandler implements ActionListener, AnalogListener{
    // Initialization
    public ServerInputHandler(InputManager inputManager){
        super(inputManager);
    }
    public void setupInputs(){
        for(ClientBinding bind : ClientBinding.values()){
            inputManager.addMapping(bind.mapping, bind.trigger);
            inputManager.addListener(this, bind.mapping);
        }
    }
    
    // Action handlers
    public void onAction(String bind, boolean down, float tpf){
        if(screen == null){
            return;
        }
        if(bind.equals(ClientBinding.LClick.toString()) && !down){
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
            if(name.equals(ClientBinding.MouseUp.toString())){
                moving.move(0, value);
            }else if(name.equals(ClientBinding.MouseDown.toString())){
                moving.move(0, -value);
            }else if(name.equals(ClientBinding.MouseRight.toString())){
                moving.move(value, 0);
            }else if(name.equals(ClientBinding.MouseLeft.toString())){
                moving.move(-value, 0);
            }
        }
        screen.onCursorMove(inputManager.getCursorPosition());
    }
}
