/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import com.jme3.math.Vector2f;
import com.jme3.math.Vector4f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;

/**
 *
 * @author SinisteRing
 */
public class UIElement {
    protected Node node = new Node("UIElement");
    protected Geometry geo;
    protected Vector4f bounds;
    protected float priority;
    
    public UIElement(Node parent, float x1, float x2, float y1, float y2, float z){
        bounds = new Vector4f(x1, x2, y1, y2);
        priority = z;
        parent.attachChild(node);
    }
    
    // Getters
    public float getPriority(){
        return priority;
    }
    
    // Check if the location given is within the bounds of the current UI element.
    public boolean withinBounds(Vector2f loc){
        if(loc.x >= bounds.x && loc.x <= bounds.y){
            if(loc.y >= bounds.z && loc.y <= bounds.w){
                return true;
            }
        }
        return false;
    }
}
