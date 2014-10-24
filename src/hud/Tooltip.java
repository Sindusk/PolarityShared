package hud;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import tools.GeoFactory;
import tools.SinText;

/**
 *
 * @author SinisteRing
 */
public class Tooltip extends HUDElement {
    protected boolean visible = false;
    protected Node parent;
    protected SinText text;
    protected Geometry background;
    
    public Tooltip(Node parent, Vector2f loc){
        super(parent, loc);
        this.parent = parent;
        visible = true;
        node.setLocalTranslation(new Vector3f(loc.x, loc.y, 1));
        text = GeoFactory.createSinTextAlpha(node, 1, new Vector3f(0, 0, 0.01f), "ES32", "Testing", ColorRGBA.White, SinText.Alignment.Center);
        text.setAlignment(SinText.Alignment.Left);
        updateBackground();
    }
    
    private void updateBackground(){
        Vector3f trans = new Vector3f(-text.getLineWidth()*0.5f,(text.getHeight()*0.5f)-(text.getLineHeight()*0.5f),0).negate();
        background = GeoFactory.createBox(node, new Vector3f(text.getLineWidth()*0.5f+0.2f, text.getHeight()*0.5f+0.1f, 0), trans, ColorRGBA.DarkGray);
    }
    
    public boolean isVisible(){
        return visible;
    }
    
    public void setLocation(Vector3f loc){
        node.setLocalTranslation(loc.x, loc.y, 1);
    }
    public void setText(String s){
        text.setText(s);
        background.removeFromParent();
        updateBackground();
    }
    public void toggleVisible(){
        visible = !visible;
        if(visible){
            parent.attachChild(node);
        }else{
            node.removeFromParent();
        }
    }
}
