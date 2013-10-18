/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package input;

import com.jme3.input.Input;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseAxisTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.input.controls.Trigger;

/**
 *
 * @author SinisteRing
 */
public enum Binding {
    MouseLeft(MouseInput.AXIS_X, true),
    MouseRight(MouseInput.AXIS_X, false),
    MouseUp(MouseInput.AXIS_Y, false),
    MouseDown(MouseInput.AXIS_Y, true),
    LClick(MouseInput.BUTTON_LEFT, MouseInput.class),
    RClick(MouseInput.BUTTON_RIGHT, MouseInput.class),
    Exit(KeyInput.KEY_ESCAPE, KeyInput.class);

    public final String mapping;
    public final int key;
    public final Trigger trigger;

    Binding(int key, boolean dir){
        mapping = this.toString();
        this.key = key;
        trigger = new MouseAxisTrigger(key, dir);
    }
    Binding(int key, Class <? extends Input> input){
        mapping = this.toString();
        this.key = key;
        if(input == MouseInput.class){
            trigger = new MouseButtonTrigger(key);
        }else{
            trigger = new KeyTrigger(key);
        }
    }
}
