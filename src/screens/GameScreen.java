/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package screens;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.scene.Node;
import entity.PlayerEntity;
import input.Binding;
import input.ClientInputHandler;
import tools.S;
import world.World;

/**
 *
 * @author SinisteRing
 */
public class GameScreen extends Screen {
    protected World world;
    protected PlayerEntity testChar;
    protected PlayerEntity testChar2;
    
    // Movement testing
    private boolean moveLeft = false;
    private boolean moveRight = false;
    private boolean moveUp = false;
    private boolean moveDown = false;
    
    private boolean moveLeft2 = false;
    private boolean moveRight2 = false;
    private boolean moveUp2 = false;
    private boolean moveDown2 = false;
    
    public GameScreen(Node rootNode, Node guiNode){
        super(rootNode, guiNode);
        name="Game Screen";
    }
    
    @Override
    public void initialize(final ClientInputHandler inputHandler) {
        world = new World(50);
        world.generate();
        root.attachChild(world.getNode());
        testChar = new PlayerEntity(root, ColorRGBA.Orange);
        testChar2 = new PlayerEntity(root, ColorRGBA.Red);
    }
    
    @Override
    public void update(float tpf){
        if(moveRight){
            testChar.move(tpf*5, 0, 0);
        }
        if(moveLeft){
            testChar.move(-tpf*5, 0, 0);
        }
        if(moveDown){
            testChar.move(0, -tpf*5, 0);
        }
        if(moveUp){
            testChar.move(0, tpf*5, 0);
        }
        
        if(moveRight2){
            testChar2.move(tpf*5, 0, 0);
        }
        if(moveLeft2){
            testChar2.move(-tpf*5, 0, 0);
        }
        if(moveDown2){
            testChar2.move(0, -tpf*5, 0);
        }
        if(moveUp2){
            testChar2.move(0, tpf*5, 0);
        }
        S.getCamera().setLocation(testChar.getLocation().add(0, 0, 50));
    }
    
    @Override
    public void onCursorMove(Vector2f cursorLoc) {
        //
    }
    
    @Override
    public void onAction(Vector2f cursorLoc, String bind, boolean down, float tpf) {
        if(bind.equals(Binding.Right.toString())){
            moveRight = down;
        }else if(bind.equals(Binding.Left.toString())){
            moveLeft = down;
        }else if(bind.equals(Binding.Down.toString())){
            moveDown = down;
        }else if(bind.equals(Binding.Up.toString())){
            moveUp = down;
        }
        
        if(bind.equals(Binding.ArrowRight.toString())){
            moveRight2 = down;
        }else if(bind.equals(Binding.ArrowLeft.toString())){
            moveLeft2 = down;
        }else if(bind.equals(Binding.ArrowDown.toString())){
            moveDown2 = down;
        }else if(bind.equals(Binding.ArrowUp.toString())){
            moveUp2 = down;
        }
    }
}
