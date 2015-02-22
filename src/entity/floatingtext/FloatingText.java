package entity.floatingtext;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import tools.GeoFactory;
import tools.SinText;
import tools.Util;

/**
 *
 * @author SinisteRing
 */
public class FloatingText {
    private static final float HOVER_HEIGHT = 1;
    private static final float FLOAT_TIME = 2;
    
    protected Node node = new Node("Floating Text");
    protected SinText label;
    protected Vector3f vector;
    protected float timer = 0;
    
    public FloatingText(Node parent, float value, int spaces){
        value = Util.roundedFloat(value, spaces);
        vector = new Vector3f(Util.randFloat(-2, 2), HOVER_HEIGHT+3, 3);
        label = GeoFactory.createSinTextAlpha(node, 0.7f, Vector3f.ZERO, "AW32", ColorRGBA.Blue, SinText.Alignment.Center);
        label.setText(String.valueOf(value));
        node.setLocalTranslation(new Vector3f(0, HOVER_HEIGHT, 3));
        parent.attachChild(node);
    }
    
    // Returns true if it needs to be destroyed.
    public boolean update(float tpf){
        timer += tpf;
        node.setLocalTranslation(new Vector3f(0, HOVER_HEIGHT, 3).interpolate(vector, timer/FLOAT_TIME));
        if(timer > FLOAT_TIME){
            return true;
        }
        return false;
    }
    
    public void destroy(){
        label.removeFromParent();
    }
}
