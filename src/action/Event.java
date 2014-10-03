/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package action;

import character.GameCharacter;
import com.jme3.math.Vector2f;
import com.jme3.scene.Node;
import java.util.ArrayList;
import tools.Sys;

/**
 *
 * @author SinisteRing
 */
public class Event {
    protected static ArrayList enumTargets(Node collisionNode, Vector2f loc, float radius){
        ArrayList targets = new ArrayList();
        
        return targets;
    }
    
    public static void damageArea(GameCharacter owner, Node collisionNode, Vector2f loc, float radius, float damage){
        ArrayList targets = enumTargets(collisionNode, loc, radius);
    }
}
