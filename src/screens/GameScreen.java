/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package screens;

import com.jme3.math.Vector2f;
import com.jme3.scene.Node;
import input.ClientInputHandler;
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
    public void initialize(final ClientInputHandler inputHandler) {
        world = new World(50);
        world.generate();
        root.attachChild(world.getNode());
    }

    @Override
    public void update(Vector2f cursorLoc) {
        //
    }

    @Override
    public void onAction(Vector2f cursorLoc, String bind, boolean down, float tpf) {
        //
    }
}
