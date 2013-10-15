/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package screens;

import com.jme3.math.Vector2f;
import com.jme3.scene.Node;
import world.World;

/**
 *
 * @author SinisteRing
 */
public class GameScreen extends Screen {
    protected World world;
    
    public GameScreen(Node rootNode, Node guiNode){
        super(rootNode, guiNode);
        name="Game Screen";
    }
    
    @Override
    public void initialize() {
        world = new World(50);
        world.generate();
        root.attachChild(world.getNode());
    }

    @Override
    public void update(Vector2f cursorLoc) {
        //
    }

    @Override
    public boolean handleClick(Vector2f cursorLoc) {
        return false;
    }

    @Override
    public boolean handleUnclick(Vector2f cursorLoc) {
        return false;
    }
    
}
