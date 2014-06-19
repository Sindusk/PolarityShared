package screens;

import com.jme3.app.Application;
import com.jme3.input.event.KeyInputEvent;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import grid.Grid;
import grid.GridNode;
import input.ClientBinding;
import input.InputHandler;
import tools.GeoFactory;
import tools.Sys;

/**
 *
 * @author SinisteRing
 */
public class GridScreen extends Screen {
    public GridScreen(Application app, Node rootNode, Node guiNode){
        super(app, rootNode, guiNode);
        name="Sphere Screen";
    }
    
    // Called when the screen first initializes
    @Override
    public void initialize(InputHandler inputHandler) {
        Sys.getCamera().setLocation(new Vector3f(0,0,100));

        Grid grid = new Grid();

        grid.addNode(new GridNode(1, new Vector3f(0,0,0)));
        grid.addNode(new GridNode(2, new Vector3f(20,0,0)));
        grid.addNode(new GridNode(3, new Vector3f(40,0,0)));
        grid.addNode(new GridNode(4, new Vector3f(20,-20,0)));
        grid.connect(grid.getNodeById(1), grid.getNodeById(2));
        grid.connect(grid.getNodeById(2), grid.getNodeById(3));
        grid.connect(grid.getNodeById(2), grid.getNodeById(4));

        for (GridNode n: grid.getNodes().values()){
            GeoFactory.createSphere(root, String.format("Node %d", n.getId()), 6, n.getPos(), ColorRGBA.Cyan);
        }
    }
    
    @Override
    public void update(float tpf){
        //
    }
    
    // Called when the mouse is moved
    @Override
    public void onCursorMove(Vector2f cursorLoc){
        // do work
    }
    
    // Called when a key is pressed or released
    @Override
    public void onAction(Vector2f cursorLoc, String bind, boolean down, float tpf) {
        // Default bind back to menu screen
        if(bind.equals(ClientBinding.Exit.toString())){
            Sys.getInputHandler().switchScreens(new MenuScreen(app, root.getParent(), gui.getParent()));
        }
    }
    
    @Override
    public void onKeyEvent(KeyInputEvent evt){
        // implement
    }
}
