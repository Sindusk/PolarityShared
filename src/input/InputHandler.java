package input;

import com.jme3.input.InputManager;
import screens.Screen;
import ui.Frame;

/**
 *
 * @author SinisteRing
 */
public abstract class InputHandler {
    public static final float MOUSE_SENSITIVITY = 1;
    protected final InputManager inputManager;
    protected Screen screen;
    public Frame moving = null;
    
    public InputHandler(InputManager inputManager){
        this.inputManager = inputManager;
    }
    
    public abstract void setupInputs();
    
    // Screen switching
    public void switchScreens(Screen newScreen){
        if(screen != null){
            screen.destroy();
        }
        screen = newScreen;
        screen.initialize(this);
    }
    
    // Update called every frame
    public void update(float tpf){
        if(screen != null){
            screen.update(tpf);
        }
    }
}
