package screens;

import character.Player;
import com.jme3.input.event.KeyInputEvent;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import hud.HUDElement;
import hud.Tooltip;
import hud.advanced.FPSCounter;
import input.Bind;
import input.InputHandler;
import items.Inventory;
import items.SpellNodeItem;
import items.creation.ItemGenerator;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import main.GameApplication;
import spellforge.SpellMatrix;
import spellforge.nodes.SpellNode;
import tools.Sys;
import ui.Button;
import ui.Menu;
import ui.UIElement;
import ui.interfaces.Draggable;
import ui.interfaces.TooltipInfo;
import ui.items.InventoryPanel;
import ui.items.ItemButton;

/**
 *
 * @author SinisteRing
 */
public class SpellForgeScreen extends Screen {
    protected ArrayList<HUDElement> hud = new ArrayList();
    protected SpellForgeScreen[] spellForges;
    protected Button[] slots = new Button[4];
    protected GameScreen gameScreen;
    protected Draggable dragging;
    protected InventoryPanel invPanel;
    protected SpellMatrix matrix;
    protected Menu menu;
    protected Tooltip tooltip;
    protected Tooltip itemTooltip;
    
    public SpellForgeScreen(GameApplication app, GameScreen gameScreen, Node root, Node gui){
        super(app, root, gui);
        this.gameScreen = gameScreen;
    }
    
    public SpellMatrix getMatrix(){
        return matrix;
    }
    
    @Override
    public void initialize(final InputHandler inputHandler) {
        this.inputHandler = inputHandler;
        
        final Player p = app.getCharManager().getPlayer(gameScreen.getPlayerID());
        Button b;
        float spacing = 0.35f;
        int i = 0;
        while(i < slots.length){
            final int slot = i;
            b = new Button(gui, new Vector2f(Sys.width*spacing, Sys.height*0.9f), 50, 50, 0){
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
        
        matrix = new SpellMatrix(gui, new Vector2f(Sys.width*0.5f, Sys.height*0.5f), 7, 7);
        invPanel = new InventoryPanel(gui, new Vector2f(Sys.width*0.15f, Sys.height*0.5f), Sys.width*0.25f, Sys.height*0.9f, 0, 5);
        invPanel.setColor(new ColorRGBA(0.1f, 0.1f, 0.1f, 1));
        invPanel.setInventory(app.getCharManager().getPlayer(gameScreen.getPlayerID()));
        invPanel.display();
        ui.add(invPanel);
        hud.add(new FPSCounter(gui, new Vector2f(10, Sys.height-15), 15));   // Creates the FPS Counter
        tooltip = new Tooltip(gui, new Vector2f(Sys.width*0.75f, Sys.height-50));
        itemTooltip = new Tooltip(gui, Vector2f.ZERO);
        itemTooltip.toggleVisible();
        
        b = new Button(gui, new Vector2f(Sys.width*0.0875f, Sys.height*0.1f), Sys.width*0.1f, Sys.height*0.03f, 1){
            @Override
            public void onAction(Vector2f cursorLoc, String bind, boolean down, float tpf){
                app.enqueue(new Callable<Void>(){
                    public Void call() throws Exception{
                        Inventory inv = p.getInventory();
                        //inv.add(new SpellNodeItem(icon[FastMath.nextRandomInt(0, icon.length-1)], type[FastMath.nextRandomInt(0, type.length-1)]));
                        inv.add(ItemGenerator.randomItem(inv, FastMath.nextRandomInt(1,50)));
                        invPanel.display();
                        return null;
                    }
                });
            }
        };
        b.setText("Add Item");
        b.setColor(ColorRGBA.Orange);
        ui.add(b);
        b = new Button(gui, new Vector2f(Sys.width*0.2125f, Sys.height*0.1f), Sys.width*0.1f, Sys.height*0.03f, 1){
            @Override
            public void onAction(Vector2f cursorLoc, String bind, boolean down, float tpf){
                app.enqueue(new Callable<Void>(){
                    public Void call() throws Exception{
                        Inventory inv = p.getInventory();
                        inv.removeRandom();
                        invPanel.display();
                        return null;
                    }
                });
            }
        };
        b.setText("Delete Item");
        b.setColor(ColorRGBA.Orange);
        ui.add(b);
    }
    
    public void setSpellForgeArray(SpellForgeScreen[] spellForges){
        this.spellForges = spellForges;
    }
    
    @Override
    public void update(float tpf) {
        Vector2f cursorLoc = app.getInputManager().getCursorPosition();
        if(dragging != null){
            dragging.moveWithOffset(cursorLoc);
        }
        for(SpellForgeScreen spellForge : spellForges){
            if(spellForge.getMatrix() != null){
                spellForge.getMatrix().update(tpf);
            }
        }
        for(HUDElement e : hud){
            e.update(null, tpf);
        }
        UIElement e = checkUI(cursorLoc);
        if(e != null && e instanceof InventoryPanel){
            InventoryPanel panel = (InventoryPanel) e;
            UIElement uielement = panel.checkControls(cursorLoc);
            if(uielement != null && uielement instanceof TooltipInfo){
                TooltipInfo ttinfo = (TooltipInfo) uielement;
                if(!itemTooltip.isVisible()){
                    itemTooltip.toggleVisible();
                }
                itemTooltip.setText(ttinfo.getTooltip(), ttinfo.getColorMap());
                itemTooltip.setLocation(cursorLoc.add(new Vector2f(50, 5)));
            }else{
                if(itemTooltip.isVisible()){
                    itemTooltip.toggleVisible();
                }
            }
        }else{
            if(itemTooltip.isVisible()){
                itemTooltip.toggleVisible();
            }
        }
        SpellNode spellNode = matrix.findNode(cursorLoc);
        if(spellNode != null){
            if(!tooltip.isVisible()){
                tooltip.toggleVisible();
            }
            tooltip.setText(spellNode.getTooltip(), spellNode.getColorMap());
        }else if(tooltip.isVisible()){
            tooltip.toggleVisible();
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
        //
    }
    
    private void createMenu(SpellNode spellNode, Vector2f cursorLoc){
        if(menu != null){
            menu.destroy(ui);
        }
        menu = new Menu(gui, cursorLoc.add(new Vector2f(100, -30)), 1);
        if(spellNode.isEmpty()){
            menu.addOption(ui, spellNode.addPowerConduitOption(menu));
            menu.addOption(ui, spellNode.addModifierConduitOption(menu));
            menu.addOption(ui, spellNode.addEffectConduitOption(menu));
        }
    }

    @Override
    public void onAction(Vector2f cursorLoc, String bind, boolean down, float tpf) {
        UIElement e = checkUI(cursorLoc);
        if(e != null){
            if(e instanceof InventoryPanel){
                InventoryPanel panel = (InventoryPanel) e;
                e = panel.checkControls(cursorLoc);
                if(e != null && e instanceof Draggable && down && bind.equals(Bind.LClick.toString())){
                    dragging = (Draggable) e;
                }
            }else if(down){
                e.onAction(cursorLoc, bind, down, tpf);
                if(menu != null){
                    menu.destroy(ui);
                }
                return;
            }
        }
        SpellNode spellNode = matrix.findNode(cursorLoc);
        if(spellNode != null){
            if(dragging != null && !down && bind.equals(Bind.LClick.toString()) && dragging instanceof ItemButton){
                ItemButton button = (ItemButton) dragging;
                if(button.getItem() instanceof SpellNodeItem){
                    SpellNodeItem item = (SpellNodeItem) button.getItem();
                    spellNode.changeData(item.getData());
                    item.getContainer().remove(item);
                    invPanel.display();
                }
            }else if(down && bind.equals(Bind.RClick.toString())){
                createMenu(spellNode, cursorLoc);
            }else if(menu != null && menu.isActive() && down){
                menu.destroy(ui);
            }
        }else if(menu != null && menu.isActive() && down){
            menu.destroy(ui);
        }
        if(dragging != null && !down && bind.equals(Bind.LClick.toString())){
            dragging.resetDragging();
            dragging = null;
        }
        if(bind.equals(Bind.Escape.toString()) && down){
            inputHandler.changeScreens(gameScreen);
        }
    }

    @Override
    public void onKeyEvent(KeyInputEvent evt) {
        // implement
    }
    
}
