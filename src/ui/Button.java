package ui;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import tools.CG;
import tools.S;
import tools.SinText;
import tools.T;

/**
 *
 * @author SinisteRing
 */
public class Button extends UIElement {
    protected SinText text;
    
    public Button(Node parent, Vector2f loc, float x, float y, float z){
        super(parent, loc, x, y, z);
        x /= 2.0f;
        y /= 2.0f;
        text = CG.createSinText(node, y, new Vector3f(0, y, 0.01f), "OCRAStd", " ", ColorRGBA.Blue, SinText.Alignment.Center);
        geo = CG.createBox(node, "test", new Vector3f(x, y, 0), Vector3f.ZERO, ColorRGBA.Blue);
    }
    
    // Sets the location of the button (centered)
    public void setLocation(Vector2f loc){
        node.setLocalTranslation(new Vector3f(loc.x, loc.y, priority));
    }
    // Sets the text of the button
    public void setText(String str){
        text.setText(str);
    }
    // Changes the color of the button box
    public void changeColor(ColorRGBA color){
        geo.setMaterial(T.getMaterial(S.getAssetManager(), color));
    }
}
