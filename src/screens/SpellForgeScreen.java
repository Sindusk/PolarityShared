package screens;

import com.jme3.input.event.KeyInputEvent;
import com.jme3.math.ColorRGBA;
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
import ui.Button;
import ui.Menu;
import ui.UIElement;

/**
 *
 * @author SinisteRing
 */
public class SpellForgeScreen extends Screen {
    protected ArrayList<HUDElement> hud = new ArrayList();
    protected SpellForgeScreen[] spellForges;
    protected Button[] slots = new Button[4];
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
    public void initialize(final InputHandler inputHandler) {
        this.inputHandler = inputHandler;
        float width = Sys.width;
        float height = Sys.height;
        
        float spacing = 0.35f;
        int i = 0;
        while(i < slots.length){
            final int slot = i;
            Button b = new Button(gui, new Vector2f(width*spacing, height*0.9f), 50, 50, 0){
                @Override
                public void onAction(Vector2f cursorLoc, String bind, boolean down, float tpf){
                    inputHandler.changeScreens(spellForges[slot]);
                }
            };
            if(this == spellForges[i]){
                b.setColor(ColorRGBA.Orange);
            }
            b.setText(""+(i+1));
            b.setTextColor(ColorRGBA.Green);
            spacing += 0.1f;
            ui.add(b);
            i++;
        }
        matrix = new SpellMatrix(gui, 7, 7);
        hud.add(new FPSCounter(gui, new Vector2f(10, Sys.height-15), 15));   // Creates the FPS Counter
        tooltip = new Tooltip(gui, new Vector2f(Sys.width*0.75f, Sys.height-50));
        //testTip = new Tooltip(gui, new Vector2f(50, 400));
    }
    
    public void setSpellForgeArray(SpellForgeScreen[] spellForges){
        this.spellForges = spellForges;
    }

    @Override
    public void update(float tpf) {
        for(SpellForgeScreen spellForge : spellForges){
            spellForge.getMatrix().update(tpf);
        }
        for(HUDElement e : hud){
            e.update(null, tpf);
        }
        if(tooltip.isVisible()){
            SpellNode spellNode = matrix.findNode(app.getInputManager().getCursorPosition());
            if(spellNode != null){
                tooltip.setText(spellNode.getTooltip());
            }
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
            menu.addOption(ui, spellNode.addModifierOption(menu));
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
            if(menu != null){
                menu.destroy(ui);
            }
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
