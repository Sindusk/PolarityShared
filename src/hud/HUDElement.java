/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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
    protected Vector2f location;
    
    public HUDElement(Node parent, Vector2f location){
        this.location = location;
        node.setLocalTranslation(location.x, location.y, 0);
        parent.attachChild(node);
    }
    
    public void setPriority(float priority){
        node.setLocalTranslation(location.x, location.y, priority);
    }
    
    public void update(Player player, float tpf){
        Util.log("[HUDElement] Error: Class does not have an update override", 1);
    }
}
