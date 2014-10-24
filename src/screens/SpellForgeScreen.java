package screens;

import com.jme3.input.event.KeyInputEvent;
import com.jme3.math.Vector2f;
import com.jme3.scene.Node;
import hud.Tooltip;
import input.InputHandler;
import main.GameApplication;
import spellforge.SpellGrid;
import spellforge.nodes.SpellNode;
import spellforge.nodes.SpellNodeData;
import tools.Sys;
import tools.Util;

/**
 *
 * @author SinisteRing
 */
public class SpellForgeScreen extends Screen {
    protected SpellGrid spellGrid;
    protected Tooltip tooltip;
    
    public SpellForgeScreen(GameApplication app, Node root, Node gui){
        super(app, root, gui);
    }
    
    @Override
    public void initialize(InputHandler inputHandler) {
        this.inputHandler = inputHandler;
        spellGrid = new SpellGrid(root, 7, 7);
        tooltip = new Tooltip(root, new Vector2f(-25, 0));
    }

    @Override
    public void update(float tpf) {
        //
    }

    @Override
    public void onCursorMove(Vector2f cursorLoc) {
        SpellNode spellNode = spellGrid.checkGrid(cursorLoc);
        if(spellNode != null){
            SpellNodeData data = spellNode.getData();
            if(!tooltip.isVisible()){
                tooltip.toggleVisible();
            }
            tooltip.setText("Empty Node\nPosition: "+data.getX()+", "+data.getY()+"\nType: N/A");
            tooltip.setLocation(Util.getWorldLoc(cursorLoc, Sys.getCamera()).add(2, -1.5f, 0));
        }else if(tooltip.isVisible()){
            tooltip.toggleVisible();
        }
    }

    @Override
    public void onAction(Vector2f cursorLoc, String bind, boolean down, float tpf) {
        if(!down){
            return;
        }
        SpellNode spellNode = spellGrid.checkGrid(cursorLoc);
        if(spellNode != null){
            spellNode.onAction(cursorLoc, bind, down, tpf);
        }
    }

    @Override
    public void onKeyEvent(KeyInputEvent evt) {
        // implement
    }
    
}
