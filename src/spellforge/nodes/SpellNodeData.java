package spellforge.nodes;

import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.network.serializing.Serializable;
import spellforge.SpellMatrix;
import tools.Util;
import tools.Util.Vector2i;

/**
 *
 * @author SinisteRing
 */
@Serializable
public class SpellNodeData {
    protected Vector2i index;
    protected Vector2f loc;
    protected String type;
    protected boolean[] connected = {false, false, false, false};
    
    public SpellNodeData(){}    // For serialization
    public SpellNodeData(int x, int y, Vector2f loc){
        this.index = new Vector2i(x, y);
        this.loc = loc;
        type = "Empty Node";
    }
    
    public void setConnection(int index, boolean value){
        connected[index] = value;
    }
    
    public Vector2i getIndex(){
        return index;
    }
    public int getX(){
        return index.x;
    }
    public int getY(){
        return index.y;
    }
    public Vector2f getLocation(){
        return loc;
    }
    public String getType(){
        return type;
    }
    public boolean[] getConnections(){
        return connected;
    }
    public Vector3f get3DLocation(){
        return new Vector3f(loc.x, loc.y, 0);
    }
    public boolean canProvide(SpellNodeData data){
        return false;
    }
    public boolean canConnect(SpellNodeData data){
        return false;
    }
    public String getTooltip(){
        return type+"\nPosition: "+index.x+", "+index.y;
    }
    public String getText(){
        return " ";
    }
    
    public void recalculate(SpellMatrix matrix){
        Util.log("Error: Recalculating Empty Node! ["+index.x+", "+index.y+"]");
    }
    
    public void update(float tpf){
        Util.log("Error: Updating Empty Node! ["+index.x+", "+index.y+"]");
    }
}
