package hud.advanced;

import character.Player;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.scene.Node;
import hud.DynamicBar;
import hud.HUDElement;
import stats.advanced.Vitals;
import ui.Label;

/**
 *
 * @author SinisteRing
 */
public class VitalDisplay extends HUDElement {
    protected DynamicBar healthBar;
    protected Label healthText;
    protected DynamicBar shieldBar;
    protected Label shieldText;
    protected DynamicBar energyBar;
    protected Label energyText;
    
    public VitalDisplay(Node parent, Vector2f location){
        super(parent, location);
        healthText = new Label(node, new Vector2f(0, 75), 20, 1);
        healthText.setColor(new ColorRGBA(0.1f, 0.1f, 0.1f, 1));
        healthText.setText("Health: 100/100");
        healthBar = new DynamicBar(node, new Vector2f(0, 75), new Vector2f(healthText.getLineWidth()+10, 25), new ColorRGBA(1, 0.3f, 0.3f, 1));
        shieldText = new Label(node, new Vector2f(0, 45), 20, 1);
        shieldText.setColor(new ColorRGBA(0.1f, 0.1f, 0.1f, 1));
        shieldText.setText("Shield: 100/100");
        shieldBar = new DynamicBar(node, new Vector2f(0, 45), new Vector2f(shieldText.getLineWidth()+10, 25), new ColorRGBA(0.3f, 0.3f, 1, 1));
        energyText = new Label(node, new Vector2f(0, 15), 20, 1);
        energyText.setColor(new ColorRGBA(0.1f, 0.1f, 0.1f, 1));
        energyText.setText("Energy: 100/100");
        energyBar = new DynamicBar(node, new Vector2f(0, 15), new Vector2f(energyText.getLineWidth()+10, 25), new ColorRGBA(0.3f, 1, 0.3f, 1));
    }
    
    @Override
    public void update(Player player, float tpf){
        Vitals vitals = player.getVitals();
        healthText.setText("Health: "+Math.round(vitals.getHealth().value())+"/"+Math.round(vitals.getHealth().getMax()));
        shieldText.setText("Shield: "+Math.round(vitals.getShield().value())+"/"+Math.round(vitals.getShield().getMax()));
    }
}
