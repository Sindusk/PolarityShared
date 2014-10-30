package screens;

import com.jme3.input.event.KeyInputEvent;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import hud.HUDElement;
import hud.Tooltip;
import hud.advanced.FPSCounter;
import input.ClientBinding;
import input.InputHandler;
import java.util.ArrayList;
import main.GameApplication;
import spellforge.SpellMatrix;
import spellforge.nodes.SpellNode;
import tools.Sys;
import ui.Menu;
import ui.UIElement;

/**
 *
 * @author SinisteRing
 */
public class SpellForgeScreen extends Screen {
    protected ArrayList<HUDElement> hud = new ArrayList();
    protected Screen altScreen;
    protected SpellMatrix matrix;
    protected Menu menu;
    protected Tooltip tooltip;
    protected Tooltip testTip;
    
    public SpellForgeScreen(GameApplication app, Screen gameScreen, Node root, Node gui){
        super(app, root, gui);
        this.altScreen = gameScreen;
    }
    
    public SpellMatrix getMatrix(){
        return matrix;
    }
    
    @Override
    public void initialize(InputHandler inputHandler) {
        this.inputHandler = inputHandler;
        matrix = new SpellMatrix(root, 9, 9);
        hud.add(new FPSCounter(gui, new Vector2f(10, Sys.height-15), 15));   // Creates the FPS Counter
        tooltip = new Tooltip(gui, new Vector2f(50, Sys.height-50));
        testTip = new Tooltip(gui, new Vector2f(50, 400));
    }

    @Override
    public void update(float tpf) {
        matrix.update(tpf);
        for(HUDElement e : hud){
            e.update(null, tpf);
        }
    }
    
    @Override
    public void setVisible(boolean show){
        super.setVisible(show);
        if(show){
            Sys.getCamera().setLocation(new Vector3f(0, 0, 50));
            Sys.getCamera().lookAt(Vector3f.ZERO, Vector3f.UNIT_Y);
        }
    }

    @Override
    public void onCursorMove(Vector2f cursorLoc) {
        SpellNode spellNode = matrix.findNode(cursorLoc);
        if(spellNode != null){
            if(!tooltip.isVisible()){
                tooltip.toggleVisible();
            }
            tooltip.setText(spellNode.getTooltip());
        }else if(tooltip.isVisible()){
            tooltip.toggleVisible();
        }
    }
    
    private void createMenu(SpellNode spellNode, Vector2f cursorLoc){
        if(menu != null){
            menu.destroy(ui);
        }
        menu = new Menu(gui, cursorLoc.add(new Vector2f(100, -30)), 1);
        if(spellNode.isEmpty()){
            menu.addOption(ui, spellNode.addGeneratorOption(menu));
            menu.addOption(ui, spellNode.addProjectileOption(menu));
            menu.addOption(ui, spellNode.addDamageModifierOption(menu));
            menu.addOption(ui, spellNode.addPowerConduitOption(menu));
            menu.addOption(ui, spellNode.addModifierConduitOption(menu));
        }
    }

    @Override
    public void onAction(Vector2f cursorLoc, String bind, boolean down, float tpf) {
        if(!down){
            return;
        }
        if(bind.equals(ClientBinding.Escape.toString())){
            inputHandler.changeScreens(altScreen);
        }
        UIElement e = checkUI(cursorLoc);
        if(e != null){
            e.onAction(cursorLoc, bind, down, tpf);
            menu.destroy(ui);
            return;
        }
        SpellNode spellNode = matrix.findNode(cursorLoc);
        if(spellNode != null){
            if(bind.equals(ClientBinding.RClick.toString()) && down){
                createMenu(spellNode, cursorLoc);
            }else if(menu != null && menu.isActive()){
                menu.destroy(ui);
            }
        }else{
            if(menu != null && menu.isActive()){
                menu.destroy(ui);
            }
        }
    }

    @Override
    public void onKeyEvent(KeyInputEvent evt) {
        // implement
    }
    
}
