package polarity.shared.ui;

import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import polarity.shared.input.Bind;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author SinisteRing
 */
public class FrameWithTabs extends Frame {
    // Constants for formatting and moving all objects on the screen
    private final float buttonNodePositionX = -0.4f;
    private final float buttonNodePositionY = 0.3f;
    private final float buttonScalingUnit = sizeX;
    private final float buttonScale = 0.1f;
    private final float buttonSpacingX = 0;
    private final float buttonSpacingY = -0.11f;
    private final float panelNodePositionX = 0.1f;
    private final float panelNodePositionY = 0;
    private final float panelSizeX = 0.75f;
    private final float panelSizeY = 0.8f;
    
    // Object variables
    protected HashMap<String,Panel> panels = new HashMap();
    protected ArrayList<Button> buttons = new ArrayList();
    protected Panel currentPanel;
    protected Node panelNode = new Node("PanelNode");
    protected Node buttonsNode = new Node("TabNode");
    protected int tabs = 0;
    
    public FrameWithTabs(Node parent, Vector2f loc, float x, float y, float z){
        super(parent, loc, x, y, z);
        node.attachChild(buttonsNode);
        node.attachChild(panelNode);
        buttonsNode.setLocalTranslation(new Vector3f(sizeX*buttonNodePositionX, sizeY*buttonNodePositionY, 0));
        panelNode.setLocalTranslation(new Vector3f(sizeX*panelNodePositionX, sizeY*panelNodePositionY, 0));
    }
    
    // Adds a tab button to the frame
    public void addTab(String icon, final String key){
        final FrameWithTabs thisFrame = this; // Creates a local variable for use within inner method declarations
        final int curTab = tabs;    // Current tab counter
        // Create the button which will swap to the tab
        Button button = new Button(buttonsNode, icon, new Vector2f((buttonScalingUnit*buttonSpacingX)*curTab,
                (buttonScalingUnit*buttonSpacingY)*curTab), buttonScalingUnit*buttonScale, buttonScalingUnit*buttonScale, 0){
            @Override
            public void onAction(Vector2f cursorLoc, String bind, boolean down, float tpf){
                if(bind.equals(Bind.LClick.toString()) && down){
                    thisFrame.setTab(curTab, key);
                }
            }
        };
        buttons.add(button);    // Registers the button
        controls.add(button);   // Allows the button to be controlled
        // Creates the panel registered to this tab, then puts it in the HashMap
        Panel panel = new Panel(panelNode, new Vector2f(0, 0), sizeX*panelSizeX, sizeY*panelSizeY, 0);
        panel.setColor(new ColorRGBA(FastMath.nextRandomFloat(), FastMath.nextRandomFloat(), 1, 1));    // Color is randomized for testing
        panel.getNode().removeFromParent();
        panels.put(key, panel);     // Puts the panel instance into the HashMap at poisition key
        tabs++;
    }
    
    // Sets the current displayed tab
    public void setTab(int tab, String key){
        // If there is a panel active, this is required for cleaning up before changing
        if(currentPanel != null){
            // Do nothing if we're already on this tab
            if(currentPanel.equals(panels.get(key))){
                return;
            }else{  // Else, remove the panel from the screen, to make way for the new tab
                currentPanel.getNode().removeFromParent();
                controls.remove(currentPanel);  // Unregisters the panel from polarity.shared.input
            }
        }
        currentPanel = panels.get(key);     // Obtain the panel related to the key passed in
        panelNode.attachChild(currentPanel.getNode());
        controls.add(currentPanel);     // Registers the new panel to polarity.shared.input
    }
    
    // Gets the panel related to the key
    public Panel getPanel(String key){
        return panels.get(key);
    }
}
