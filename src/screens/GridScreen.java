package screens;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import tools.CG;

/**
 *
 * @author SinisteRing
 */
public class GridScreen extends Screen {
    
    public GridScreen(Node rootNode, Node guiNode){
        super(rootNode, guiNode);
        name="Sphere Screen";
    }
    
    // Called when the screen first initializes
    @Override
    public void initialize() {
        CG.createBox(gui, "test box", new Vector3f(50, 50, 0), new Vector3f(400, 300, 0), ColorRGBA.Blue);
    }
    
    // Called when a click is sent to the screen
    @Override
    public boolean handleClick(Vector2f cursorLoc) {
        return false;
    }
    
    // Called when a click is released on the screen
    @Override
    public boolean handleUnclick(Vector2f cursorLoc) {
        return false;
    }
    
}
