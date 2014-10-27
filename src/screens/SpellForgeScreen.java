package screens;

import com.jme3.input.event.KeyInputEvent;
import com.jme3.math.Vector2f;
import com.jme3.scene.Node;
import hud.Tooltip;
import input.ClientBinding;
import input.InputHandler;
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
    protected SpellMatrix spellGrid;
    protected Menu menu;
    protected Tooltip tooltip;
    
    public SpellForgeScreen(GameApplication app, Node root, Node gui){
        super(app, root, gui);
    }
    
    @Override
    public void initialize(InputHandler inputHandler) {
        this.inputHandler = inputHandler;
        spellGrid = new SpellMatrix(root, 7, 7);
        tooltip = new Tooltip(gui, new Vector2f(50, Sys.height-100));
    }

    @Override
    public void update(float tpf) {
        //
    }

    @Override
    public void onCursorMove(Vector2f cursorLoc) {
        SpellNode spellNode = spellGrid.checkGrid(cursorLoc);
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
            menu.addOption(ui, spellNode.addPowerConduitOption(menu));
            menu.addOption(ui, spellNode.addProjectileOption(menu));
        }
    }

    @Override
    public void onAction(Vector2f cursorLoc, String bind, boolean down, float tpf) {
        if(!down){
            return;
        }
        UIElement e = checkUI(cursorLoc);
        if(e != null){
            e.onAction(cursorLoc, bind, down, tpf);
            menu.destroy(ui);
            return;
        }
        SpellNode spellNode = spellGrid.checkGrid(cursorLoc);
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
