/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package screens;

import com.jme3.app.Application;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import entity.PlayerEntity;
import input.ClientBinding;
import input.InputHandler;
import main.GameClient;
import player.PlayerManager;
import tools.Sys;
import tools.Util;
import world.World;

/**
 *
 * @author SinisteRing
 */
public class GameScreen extends Screen {
    protected World world;
    protected PlayerManager playerManager;
    protected PlayerEntity testChar2;
    
    // Movement testing
    protected int playerID;
    
    private boolean moveLeft2 = false;
    private boolean moveRight2 = false;
    private boolean moveUp2 = false;
    private boolean moveDown2 = false;
    
    public GameScreen(Application app, Node rootNode, Node guiNode){
        super(app, rootNode, guiNode);
        playerManager = clientNetwork.getPlayerManager();
        playerID = clientNetwork.getID();
        if(Sys.debug > 3){
            Util.log("[GameScreen] <Initialize> playerID = "+playerID);
        }
        name="Game Screen";
    }
    
    @Override
    public void initialize(final InputHandler inputHandler) {
        world = new World(50);
        world.generate();
        root.attachChild(world.getNode());
        root.attachChild(playerManager.getNode());
        testChar2 = new PlayerEntity(root, ColorRGBA.Red);
    }
    
    @Override
    public void update(float tpf){
        if(Sys.debug > 4){
            Util.log("[GameScreen] <update> playerID = "+playerID);
        }
        playerManager.getPlayer(playerID).update(tpf);
        
        if(moveRight2){
            testChar2.move(tpf*5, 0);
        }
        if(moveLeft2){
            testChar2.move(-tpf*5, 0);
        }
        if(moveDown2){
            testChar2.move(0, -tpf*5);
        }
        if(moveUp2){
            testChar2.move(0, tpf*5);
        }
        Vector2f tempVect=playerManager.getPlayer(playerID).getLocation();
        Sys.getCamera().setLocation(new Vector3f(tempVect.x, tempVect.y, 50));
    }
    
    @Override
    public void onCursorMove(Vector2f cursorLoc) {
        //
    }
    
    @Override
    public void onAction(Vector2f cursorLoc, String bind, boolean down, float tpf) {
        if(bind.equals(ClientBinding.Up.toString())){
            playerManager.getPlayer(playerID).setMovement(0, down);
        }else if(bind.equals(ClientBinding.Right.toString())){
            playerManager.getPlayer(playerID).setMovement(1, down);
        }else if(bind.equals(ClientBinding.Down.toString())){
            playerManager.getPlayer(playerID).setMovement(2, down);
        }else if(bind.equals(ClientBinding.Left.toString())){
            playerManager.getPlayer(playerID).setMovement(3, down);
        }
        
        if(bind.equals(ClientBinding.ArrowRight.toString())){
            moveRight2 = down;
        }else if(bind.equals(ClientBinding.ArrowLeft.toString())){
            moveLeft2 = down;
        }else if(bind.equals(ClientBinding.ArrowDown.toString())){
            moveDown2 = down;
        }else if(bind.equals(ClientBinding.ArrowUp.toString())){
            moveUp2 = down;
        }
    }
}
