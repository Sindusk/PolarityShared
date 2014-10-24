package spellforge.nodes;

import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.network.serializing.Serializable;
import tools.Util.Vector2i;

/**
 *
 * @author SinisteRing
 */
@Serializable
public class SpellNodeData {
    protected Vector2i index;
    protected Vector2f loc;
    
    public SpellNodeData(){}    // For serialization
    public SpellNodeData(int x, int y, Vector2f loc){
        this.index = new Vector2i(x, y);
        this.loc = loc;
    }
    
    public int getX(){
        return index.x;
    }
    public int getY(){
        return index.y;
    }
    public Vector3f get3DLocation(){
        return new Vector3f(loc.x, loc.y, 0);
    }
}
