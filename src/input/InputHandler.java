package input;

import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import main.GameApplication;
import screens.Screen;
import tools.Sys;
import ui.Frame;

/**
 *
 * @author SinisteRing
 */
public abstract class InputHandler {
    public static final float MOUSE_SENSITIVITY = 1;
    protected GameApplication app;
    protected Screen screen;
    public Frame moving = null;
    
    public InputHandler(GameApplication app){
        this.app = app;
    }
    
    // Getters
    public Screen getScreen(){
        return screen;
    }
    
    public abstract void setupInputs();
    
    public void changeScreens(Screen newScreen){
        screen.setVisible(false);
        newScreen.setVisible(true);
        screen = newScreen;
    }
    
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
    
    /**
     * Obtains the cursor location in world space.
     * @return Location (in world space) of the cursor
     */
    public Vector2f getCursorLocation(){
        Vector3f worldPos = Sys.getCamera().getWorldCoordinates(app.getInputManager().getCursorPosition(), 0f).clone();
        Vector2f worldPos2D = new Vector2f(worldPos.x, worldPos.y);
        return worldPos2D;
    }
}
