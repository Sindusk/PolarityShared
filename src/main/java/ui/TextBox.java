/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import com.jme3.input.event.KeyInputEvent;
import com.jme3.math.Vector2f;
import com.jme3.scene.Node;

/**
 *
 * @author SinisteRing
 */
public class TextBox extends Button {
    private String boxText;
    
    public TextBox(Node parent, Vector2f center, float x, float y, float z){
        super(parent, center, x, y, z);
        boxText = "Test";
        text.setText(boxText);
    }
    
    private void append(String s){
        boxText += s;
        text.setText(boxText);
    }
    
    // Input for the text box
    @Override
    public void onKeyEvent(KeyInputEvent evt){
        if(evt.getKeyChar() == ' ' && evt.getKeyCode() != 57){
        }else if(Character.isDefined(evt.getKeyChar())){
            append(evt.getKeyChar()+"");
        }else{
            append(" ");
        }
    }
}
