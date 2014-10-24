package spellforge.nodes;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.math.Vector4f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import tools.GeoFactory;
import tools.Util;

/**
 *
 * @author SinisteRing
 */
public class SpellNode {
    public static final float SIZE = 4;
    public static final float size = SIZE*0.5f;
    
    protected SpellNodeData data;
    protected Vector4f bounds;
    protected Geometry geo;
    
    public SpellNode(Node parent, SpellNodeData data){
        this.data = data;
        Vector3f center = data.get3DLocation();
        geo = GeoFactory.createBox(parent, new Vector3f(size-0.05f, size-0.05f, 0f), center, ColorRGBA.Gray);
        Vector3f offset = Util.getOffset(parent);
        bounds = new Vector4f(offset.x+center.x-size, offset.x+center.x+size, offset.y+center.y-size, offset.y+center.y+size);
    }
    
    public SpellNodeData getData(){
        return data;
    }
    
    public void onAction(Vector2f cursorLoc, String bind, boolean down, float tpf){
        //
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
