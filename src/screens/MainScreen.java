package screens;

import com.jme3.collision.CollisionResult;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import input.ClientInputHandler;
import stats.Health;
import stats.Mana;
import stats.Stat;
import stats.StatList;
import tools.CG;
import tools.S;
import tools.SinText;
import tools.T;

/**
 *
 * @author SinisteRing
 */
public class MainScreen {
    protected static Node node = new Node("MainScreen");
    protected static boolean active = true;
    
    public static boolean isActive(){
        return active;
    }
    
    private static StatList list = new StatList();
    // Called when the user presses left click.
    public static void handleClick(){
        Vector2f mouseLoc = S.getInputManager().getCursorPosition();
        CollisionResult result = T.getMouseTarget(mouseLoc, S.getCamera(), node);
        if(result == null){
            return;
        }
    }
    
    // Called when the user releases left click.
    public static void handleUnclick(){
        //
    }
    
    // Called when the mouse is moved.
    public static void update(){
        //
    }
    
    // Initialize the Main Screen.
    public static void initialize(Node root){
        // Initialize camera location and facing.
        S.getCamera().setLocation(new Vector3f(0, 0, 50));
        S.getCamera().lookAt(Vector3f.ZERO, Vector3f.UNIT_Y);
        
        // Initialize input.
        ClientInputHandler.initializeMenuInput();
        
        // Testing
        CG.createBox(node, "game", new Vector3f(10, 1, 0), new Vector3f(0, 10, 0), ColorRGBA.Blue);
        CG.createSinText(node, 1, new Vector3f(0, 11, 0), "OCRAStd", "Start Game", ColorRGBA.White, SinText.Alignment.Center);
        CG.createBox(node, "inventory", new Vector3f(10, 1, 0), new Vector3f(0, 5, 0), ColorRGBA.Blue);
        CG.createSinText(node, 1, new Vector3f(0, 6, 0), "OCRAStd", "Inventory", ColorRGBA.White, SinText.Alignment.Center);
        CG.createBox(node, "exit", new Vector3f(10, 1, 0), new Vector3f(0, 0, 0), ColorRGBA.Blue);
        CG.createSinText(node, 1, new Vector3f(0, 1, 0), "OCRAStd", "Exit", ColorRGBA.White, SinText.Alignment.Center);
        
        // Finally, attach the node to the root.
        root.attachChild(node);
    }
}
