package screens;

import com.jme3.input.event.KeyInputEvent;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import input.Bind;
import input.InputHandler;
import character.CharacterManager;
import character.Player;
import com.jme3.math.ColorRGBA;
import hud.advanced.ActionBar;
import hud.advanced.FPSCounter;
import hud.HUDElement;
import hud.advanced.Locator;
import hud.advanced.VitalDisplay;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import main.GameApplication;
import netdata.requests.SpellMatrixRequest;
import spellforge.SpellMatrix;
import tools.Sys;
import tools.Util;
import ui.Button;
import ui.UIElement;
import ui.advanced.GameMenu;
import world.Chunk;

/**
 * Screen encompassing Gameplay.
 * @author Sindusk
 */
public class GameScreen extends Screen {
    protected ArrayList<HUDElement> hud = new ArrayList();
    protected SpellForgeScreen spellForge;
    protected GameMenu gameMenu;
    protected CharacterManager charManager;
    protected Chunk chunk;
    
    // Movement testing
    protected int playerID;
    
    public GameScreen(GameApplication app, Node rootNode, Node guiNode){
        super(app, rootNode, guiNode);
        Util.log("[GameScreen] <Initialize> playerID = "+playerID, 3);
        gameMenu = new GameMenu(gui, new Vector2f(Sys.width*0.5f, Sys.height*0.5f), 2);
        charManager = app.getCharManager();
        playerID = clientNetwork.getID();
        charManager.setMyID(playerID);
        name="Game Screen";
    }
    public Player getPlayer(){
        return charManager.getPlayer(playerID);
    }
    
    @Override
    public void initialize(final InputHandler inputHandler) {
        this.inputHandler = inputHandler;
        hud.add(new FPSCounter(gui, new Vector2f(10, Sys.height-15), 15));  // Creates the FPS Counter
        hud.add(new Locator(gui, new Vector2f(10, Sys.height-35), 15));     // Creates the Locator
        hud.add(new VitalDisplay(gui, new Vector2f(150, 50)));              // Creates resource displays
        hud.add(new ActionBar(gui, new Vector2f(Sys.width*0.5f, ActionBar.GEO_SIZE*1.5f))); // Creates the bottom action bar
        
        // Buttons for Game Menu
        // Return to Game button:
        Button returnButton = new Button(gameMenu.getNode(), new Vector2f(0, Sys.height*0.08f), Sys.width*0.4f, Sys.height*0.05f, 0){
            @Override
            public void onAction(Vector2f cursorLoc, String bind, boolean down, float tpf){
                if(bind.equals(Bind.LClick.toString()) && down){
                    gameMenu.setVisible(ui, false);
                }
            }
        };
        returnButton.setText("Return to Game");
        returnButton.setColor(ColorRGBA.Red);
        gameMenu.addOption(ui, returnButton);
        ui.remove(returnButton);
        
        // Spell Matrix button:
        Button spellMatrixButton = new Button(gameMenu.getNode(), new Vector2f(0, 0), Sys.width*0.4f, Sys.height*0.05f, 0){
            @Override
            public void onAction(Vector2f cursorLoc, String bind, boolean down, float tpf){
                if(bind.equals(Bind.LClick.toString()) && down){
                    clientNetwork.send(new SpellMatrixRequest(playerID));
                    inputHandler.changeScreens(spellForge);
                }
            }
        };
        spellMatrixButton.setText("Spell Matrix");
        spellMatrixButton.setColor(ColorRGBA.Red);
        gameMenu.addOption(ui, spellMatrixButton);
        ui.remove(spellMatrixButton);
        
        // Spell Matrix button:
        Button exitButton = new Button(gameMenu.getNode(), new Vector2f(0, -Sys.height*0.08f), Sys.width*0.4f, Sys.height*0.05f, 0){
            @Override
            public void onAction(Vector2f cursorLoc, String bind, boolean down, float tpf){
                if(bind.equals(Bind.LClick.toString()) && down){
                    app.stop();
                }
            }
        };
        exitButton.setText("Exit");
        exitButton.setColor(ColorRGBA.Red);
        gameMenu.addOption(ui, exitButton);
        ui.remove(exitButton);
        
        spellForge = new SpellForgeScreen(app, this, Screen.getTopRoot(), Screen.getTopGUI());
        spellForge.initialize(inputHandler);
        spellForge.setVisible(false);
        
        root.attachChild(app.getWorld().getNode());
        root.attachChild(charManager.getNode());
    }
    
    @Override
    public void changeInit(){
        getPlayer().setAttacking(false);
    }
    
    @Override
    public void update(final float tpf){
        charManager.getPlayer(playerID).set3DMouseLocation(inputHandler.get3DCursorLocation());
        charManager.getPlayer(playerID).updateLocal(tpf, app.getInputManager().getCursorPosition());
        
        // For multithreading of heavy CPU usage loops, create an enqueue:
        app.enqueue(new Callable<Void>(){
            public Void call() throws Exception{
                charManager.update(tpf);
                return null;
            }
        });
        app.enqueue(new Callable<Void>(){
            public Void call() throws Exception{
                app.getWorld().update(tpf);
                return null;
            }
        });
        app.enqueue(new Callable<Void>(){
            public Void call() throws Exception{
                app.getWorld().reloadChunks(getPlayer());
                return null;
            }
        });
        
        for(SpellMatrix matrix : charManager.getPlayer(playerID).getMatrixArray()){
            if(matrix != null){
                matrix.update(tpf);
            }
        }
        
        // Update all HUD elements
        for(HUDElement h : hud){
            h.update(charManager.getPlayer(playerID), tpf);
        }
        
        Vector2f tempVect = charManager.getPlayer(playerID).getLocation();
        Sys.getCamera().setLocation(new Vector3f(tempVect.x, tempVect.y, 40));
        Chunk newChunk = app.getWorld().getChunk(tempVect);
        if(newChunk != chunk){
            app.getWorld().reloadChunks(newChunk);
            chunk = newChunk;
        }
        clientNetwork.update(tpf);
    }
    
    @Override
    public void onCursorMove(Vector2f cursorLoc) {
        //
    }
    
    @Override
    public void onAction(Vector2f cursorLoc, String bind, boolean down, float tpf) {
        UIElement e = checkUI(cursorLoc);
        if(e != null){
            e.onAction(cursorLoc, bind, down, tpf);
        }
        // Actions
        if(bind.equals(Bind.LClick.toString())){
            getPlayer().setAttacking(down);
        }else if(bind.equals(Bind.RClick.toString()) && down){
            //app.getWorld().getBlock(Util.getWorldLoc(cursorLoc, Sys.getCamera()));
        // Movement
        }else if(bind.equals(Bind.W.toString())){
            getPlayer().setMovement(0, down);
        }else if(bind.equals(Bind.D.toString())){
            getPlayer().setMovement(1, down);
        }else if(bind.equals(Bind.S.toString())){
            getPlayer().setMovement(2, down);
        }else if(bind.equals(Bind.A.toString())){
            getPlayer().setMovement(3, down);
        }else if(bind.equals(Bind.Escape.toString()) && down && !gameMenu.isActive()){
            gameMenu.setVisible(ui, true);
        }
    }
    
    @Override
    public void onKeyEvent(KeyInputEvent evt){
        // implement
    }
}
