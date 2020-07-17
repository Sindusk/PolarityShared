package spellforge.nodes;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.network.serializing.Serializable;
import items.data.SpellNodeItemData;
import java.util.ArrayList;
import java.util.HashMap;
import spellforge.SpellMatrix;
import tools.Util;
import tools.Vector2i;

/**
 *
 * @author SinisteRing
 */
@Serializable
public class SpellNodeData {
    protected ArrayList<SpellNodeData> granted = new ArrayList();
    protected SpellNodeItemData item;
    protected HashMap<String,Float> properties = new HashMap();
    protected Vector2i index;
    protected Vector2f loc;
    protected String name = "Spell Node";
    protected String type = "Empty Node";
    protected String icon = "empty";
    protected ColorRGBA typeColor = ColorRGBA.Gray;
    protected boolean[] connected = {false, false, false, false};
    
    public SpellNodeData(){}    // For serialization
    public SpellNodeData(int x, int y, Vector2f loc){
        this.index = new Vector2i(x, y);
        this.loc = loc;
    }
    
    public void setItem(SpellNodeItemData item){
        this.item = item;
    }
    public void setIndex(Vector2i index){
        this.index = index;
    }
    public void setLocation(Vector2f loc){
        this.loc = loc;
    }
    public void setConnection(int index, boolean value){
        this.connected[index] = value;
    }
    
    public float getProperty(String key){
        return properties.get(key);
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
    public String getIcon(){
        return icon;
    }
    public String getName(){
        return name;
    }
    public String getType(){
        return type;
    }
    public ColorRGBA getTypeColor(){
        return typeColor;
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
    public boolean canTravel(SpellNodeData data){
        return false;
    }
    public String getText(){
        return " ";
    }
    
    public SpellNodeItemData toItem(){
        return item;
    }
    
    public SpellNodeData cleanData(SpellNodeData data){
        this.index = new Vector2i(data.getX(), data.getY());
        this.loc = data.getLocation();
        this.item = null;
        return this;
    }
    
    public HashMap<String,Float> getSpellNodeProperties(){
        return properties;
    }
    public HashMap<String,Float> genProperties(int level){
        Util.log("[SpellNodeData] Critical Error: No override on genProperties()");
        return null;
    }
    public void preRecalculate(){
        granted = new ArrayList();
    }
    public void recalculate(SpellMatrix matrix){
        Util.log("Error: Recalculating Empty Node! ["+index.x+", "+index.y+"]");
    }
    
    public void update(float tpf){
        Util.log("Error: Updating Empty Node! ["+index.x+", "+index.y+"]");
    }
}
