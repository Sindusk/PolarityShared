/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hud;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.scene.Node;
import tools.SinText;
import ui.Label;

/**
 *
 * @author SinisteRing
 */
public class FPSCounter {
    protected Label label;
    
    public FPSCounter(Node parent, Vector2f location, float height, float z){
        label = new Label(parent, location, height, z);
        label.setTextAlign(SinText.Alignment.Left);
        label.setColor(ColorRGBA.White);
    }
    
    public void update(float tpf){
        label.setText("FPS: "+Math.round(1.0/tpf));
    }
}
