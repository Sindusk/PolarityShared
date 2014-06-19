package input;

import com.jme3.input.InputManager;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import screens.Screen;
import tools.Sys;
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
    
    // Getters
    public Screen getScreen(){
        return screen;
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
    
    public Vector2f getCursorLocation(){
        Vector3f worldPos = Sys.getCamera().getWorldCoordinates(inputManager.getCursorPosition(), 0f).clone();
        Vector2f worldPos2D = new Vector2f(worldPos.x, worldPos.y);
        //inputManager.getCursorPosition();
        return worldPos2D;
    }
}
