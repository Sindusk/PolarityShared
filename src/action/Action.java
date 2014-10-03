/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package action;

import character.GameCharacter;
import com.jme3.math.Vector2f;

/**
 *
 * @author SinisteRing
 */
public class Action {
    protected GameCharacter owner;
    protected Vector2f start;
    protected Vector2f target;
    
    public Action(GameCharacter owner, Vector2f start, Vector2f target){
        this.owner = owner;
        this.start = start;
        this.target = target;
    }
    
    public GameCharacter getOwner(){
        return owner;
    }
}
