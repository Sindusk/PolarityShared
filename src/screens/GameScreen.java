package screens;

import com.jme3.app.Application;
import com.jme3.input.event.KeyInputEvent;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import input.ClientBinding;
import input.InputHandler;
import character.CharacterManager;
import hud.advanced.FPSCounter;
import hud.HUDElement;
import hud.advanced.Locator;
import java.util.ArrayList;
import tools.Sys;
import tools.Util;
import world.World;

/**
 * Screen encompassing Gameplay.
 * @author Sindusk
 */
public class GameScreen extends Screen {
    protected World world;
    protected CharacterManager characterManager;
    protected ArrayList<HUDElement> hud = new ArrayList();
    
    // Movement testing
    protected int playerID;
    
    public GameScreen(Application app, Node rootNode, Node guiNode){
        super(app, rootNode, guiNode);
        characterManager = clientNetwork.getPlayerManager();
        playerID = clientNetwork.getID();
        characterManager.setMyID(playerID);
        Util.log("[GameScreen] <Initialize> playerID = "+playerID, 3);
        name="Game Screen";
    }
    
    @Override
    public void initialize(final InputHandler inputHandler) {
        this.inputHandler = inputHandler;
        world = new World(50);
        world.generate();
        hud.add(new FPSCounter(gui, new Vector2f(10, Sys.height-15), 15));   // Creates the FPS Counter
        hud.add(new Locator(gui, new Vector2f(10, Sys.height-35), 15));      // Creates the Locator
        root.attachChild(world.getNode());
        root.attachChild(characterManager.getNode());
    }
    
    @Override
    public void update(float tpf){
        Util.log("[GameScreen] <update> playerID = "+playerID, 4);
        characterManager.getPlayer(playerID).setMousePosition(inputHandler.getCursorLocation());
        characterManager.getPlayer(playerID).updateMovement(tpf);
        characterManager.update(tpf);   // Update all other players
        world.update(tpf);
        
        // Update all HUD elements
        for(HUDElement h:hud){
            h.update(characterManager.getPlayer(playerID), tpf);
        }
        
        Vector2f tempVect = characterManager.getPlayer(playerID).getLocation();
        Sys.getCamera().setLocation(new Vector3f(tempVect.x, tempVect.y, 40));
        clientNetwork.update(tpf);
    }
    
    @Override
    public void onCursorMove(Vector2f cursorLoc) {
        //
    }
    
    @Override
    public void onAction(Vector2f cursorLoc, String bind, boolean down, float tpf) {
        // Actions
        if(bind.equals(ClientBinding.LClick.toString())){
            characterManager.getPlayer(playerID).attack(cursorLoc, down);
        }else if(bind.equals(ClientBinding.RClick.toString()) && down){
            world.getBlock(Util.getWorldLoc(cursorLoc, Sys.getCamera()));
        // Movement
        }else if(bind.equals(ClientBinding.Up.toString())){
            characterManager.getPlayer(playerID).setMovement(0, down);
        }else if(bind.equals(ClientBinding.Right.toString())){
            characterManager.getPlayer(playerID).setMovement(1, down);
        }else if(bind.equals(ClientBinding.Down.toString())){
            characterManager.getPlayer(playerID).setMovement(2, down);
        }else if(bind.equals(ClientBinding.Left.toString())){
            characterManager.getPlayer(playerID).setMovement(3, down);
        }
    }
    
    @Override
    public void onKeyEvent(KeyInputEvent evt){
        // implement
    }
}
