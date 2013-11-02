package ui;

import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import input.Binding;
import java.util.ArrayList;

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
    protected ArrayList<Panel> panels = new ArrayList();
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
    public void addTab(String icon){
        final FrameWithTabs thisFrame = this;
        final int curTab = tabs;
        Button button = new Button(buttonsNode, icon, new Vector2f((buttonScalingUnit*buttonSpacingX)*curTab,
                (buttonScalingUnit*buttonSpacingY)*curTab), buttonScalingUnit*buttonScale, buttonScalingUnit*buttonScale, 0){
            @Override
            public void onAction(Vector2f cursorLoc, String bind, boolean down, float tpf){
                if(bind.equals(Binding.LClick.toString()) && down){
                    thisFrame.setTab(curTab);
                }
            }
        };
        buttons.add(button);
        controls.add(button);
        Panel panel = new Panel(panelNode, new Vector2f(0, 0), sizeX*panelSizeX, sizeY*panelSizeY, 0);
        panel.setColor(new ColorRGBA(FastMath.nextRandomFloat(), FastMath.nextRandomFloat(), 1, 1));
        panel.getNode().removeFromParent();
        panels.add(panel);
        tabs++;
    }
    
    public void setTab(int tab){
        if(currentPanel != null){
            if(currentPanel.equals(panels.get(tab))){
                return;
            }else{
                currentPanel.getNode().removeFromParent();
                controls.remove(currentPanel);
            }
        }
        currentPanel = panels.get(tab);
        panelNode.attachChild(currentPanel.getNode());
        controls.add(currentPanel);
    }
}
