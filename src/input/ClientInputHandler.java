package input;

import com.jme3.input.InputManager;
import com.jme3.input.RawInputListener;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.event.JoyAxisEvent;
import com.jme3.input.event.JoyButtonEvent;
import com.jme3.input.event.KeyInputEvent;
import com.jme3.input.event.MouseButtonEvent;
import com.jme3.input.event.MouseMotionEvent;
import com.jme3.input.event.TouchEvent;
import tools.Util;

/**
 * ClientInputHandler - Handles all input from users and organizes them based on conditions.
 * @author SinisteRing
 */
public class ClientInputHandler extends InputHandler implements ActionListener, AnalogListener, RawInputListener{
    // Initialization
    public ClientInputHandler(InputManager inputManager){
        super(inputManager);
    }
    public void setupInputs(){
        for(ClientBinding bind : ClientBinding.values()){
            inputManager.addMapping(bind.mapping, bind.trigger);
            inputManager.addListener(this, bind.mapping);
        }
        inputManager.addRawInputListener(this);
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
    public void onKeyEvent(KeyInputEvent evt){
        if(evt.isPressed()){
            Util.log(evt.getKeyChar()+" = "+evt.getKeyCode(), 3);
        }
        if(screen == null){
            return;
        }
        screen.onKeyEvent(evt);
    }
    
    // Excess methods because of RawInputListener implementation
    public void beginInput() {}
    public void endInput() {}
    public void onJoyAxisEvent(JoyAxisEvent evt) {}
    public void onJoyButtonEvent(JoyButtonEvent evt) {}
    public void onMouseMotionEvent(MouseMotionEvent evt) {}
    public void onMouseButtonEvent(MouseButtonEvent evt) {}
    public void onTouchEvent(TouchEvent evt) {}
}
