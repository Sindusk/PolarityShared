package polarity.shared.entity.nameplates;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import polarity.shared.entity.LivingEntity;
import polarity.shared.hud.DynamicBar;
import polarity.shared.tools.GeoFactory;
import polarity.shared.tools.SinText;

/**
 *
 * @author SinisteRing
 */
public class Nameplate {
    public static final float BACKGROUND_Z = 1;
    public static final float CONTENTS_Z = 2;
    
    protected Node node = new Node("Nameplate");
    protected SinText tag;
    protected Geometry background;  // Transparent background to make the name stand out
    protected DynamicBar healthBar;
    protected DynamicBar shieldBar;
    
    public Nameplate(Node parent, String name, boolean isLiving){
        // Create the hovering text above the model for the player's nametag
        tag = GeoFactory.createSinTextAlpha(node, 0.7f, new Vector3f(0, 2f, CONTENTS_Z), "AW32", name, new ColorRGBA(1, 1, 1, 1), SinText.Alignment.Center);
        // Transparent background for the name to stand out
        Vector3f size = new Vector3f(tag.getLineWidth()/2f, tag.getLineHeight()/2f, 0);
        Vector3f loc = new Vector3f(0, tag.getLocalTranslation().y, BACKGROUND_Z);
        background = GeoFactory.createBoxAlpha(node, size, loc, new ColorRGBA(0, 0, 0, 0.5f));
        // Health Bar
        if(isLiving){
            healthBar = new DynamicBar(node, new Vector2f(0, 1.5f), new Vector2f(tag.getLineWidth(), 0.3f), ColorRGBA.Red);
            healthBar.setAlign(DynamicBar.Alignment.Left);
            healthBar.setPriority(CONTENTS_Z);
            shieldBar = new DynamicBar(node, new Vector2f(0, 1.5f), new Vector2f(tag.getLineWidth(), 0.3f), new ColorRGBA(0, 0, 1, 0.2f));
            shieldBar.setAlign(DynamicBar.Alignment.Left);
            shieldBar.setPriority(CONTENTS_Z);
        }
        parent.attachChild(node);
    }
    
    public void setTextColor(ColorRGBA color){
        tag.setColor(color);
    }
    public void setLocalTranslation(Vector3f loc){
        node.setLocalTranslation(loc);
    }
    
    public void updateName(String name){
        tag.setText(name);
    }
    public void update(LivingEntity entity){
        LivingEntity le = (LivingEntity) entity;
        healthBar.updateSize(le.getVitals().getHealth().value() / le.getVitals().getHealth().getMax());
        shieldBar.updateSize(le.getVitals().getShield().value() / le.getVitals().getShield().getMax());
    }
    
    public void destroy(){
        node.removeFromParent();
    }
}
