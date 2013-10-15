package screens;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import grid.Grid;
import grid.GridNode;
import tools.CG;
import tools.S;

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
        S.getCamera().setLocation(new Vector3f(0,0,100));

        Grid grid = new Grid();

        grid.addNode(new GridNode(1, new Vector3f(0,0,0)));
        grid.addNode(new GridNode(2, new Vector3f(20,0,0)));
        grid.addNode(new GridNode(3, new Vector3f(40,0,0)));
        grid.addNode(new GridNode(4, new Vector3f(20,-20,0)));
        grid.connect(grid.getNodeById(1), grid.getNodeById(2));
        grid.connect(grid.getNodeById(2), grid.getNodeById(3));
        grid.connect(grid.getNodeById(2), grid.getNodeById(4));

        for (GridNode n: grid.getNodes().values()){
            CG.createSphere(root, String.format("Node %d", n.getId()), 6, n.getPos(), ColorRGBA.Cyan);
        }
    }
    
    // Called when the mouse is moved
    @Override
    public void update(Vector2f cursorLoc){
        // do work
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
