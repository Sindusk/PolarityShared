package polarity.shared.ui;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import polarity.shared.tools.GeoFactory;
import polarity.shared.tools.SinText;

/**
 *
 * @author SinisteRing
 */
public class Label extends UIElement {
    protected SinText label;
    
    public Label(Node parent, Vector2f center, float height, float z){
        super(parent, center, 0, height, z);
        label = GeoFactory.createSinText(node, height, new Vector3f(0, 0, 0.01f), "ES32", "Testing", ColorRGBA.Blue, SinText.Alignment.Center);
    }
    
    public float getLineWidth(){
        return label.getLineWidth();
    }
    public void setText(String text){
        label.setText(text);
    }
    @Override
    public void setColor(ColorRGBA color){
        label.setColor(color);
    }
    public void setTextAlign(SinText.Alignment align){
        label.setAlignment(align);
    }
}
