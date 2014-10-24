package hud;

import character.Player;
import com.jme3.math.Vector2f;
import com.jme3.scene.Node;
import tools.Util;

/**
 *
 * @author Sindusk
 */
public class HUDElement {
    protected Node node = new Node("HUDElement Node");
    protected Vector2f loc;
    
    public HUDElement(Node parent, Vector2f loc){
        this.loc = loc;
        node.setLocalTranslation(loc.x, loc.y, 0);
        parent.attachChild(node);
    }
    
    public void setPriority(float priority){
        node.setLocalTranslation(loc.x, loc.y, priority);
    }
    
    public void update(Player player, float tpf){
        Util.log("[HUDElement] Error: Class does not have an update override", 1);
    }
}
