package polarity.shared.spellforge.nodes;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.math.Vector4f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import polarity.shared.items.Item;
import polarity.shared.items.data.SpellNodeItemData;
import java.util.HashMap;
import polarity.shared.spellforge.SpellMatrix;
import polarity.shared.tools.GeoFactory;
import polarity.shared.tools.SinText;
import polarity.shared.tools.Util;
import polarity.shared.tools.Vector2i;
import polarity.shared.ui.interfaces.TooltipInfo;

/**
 *
 * @author SinisteRing
 */
public class SpellNode implements TooltipInfo {
    protected static final ColorRGBA PROPERTY_NAME_COLOR = new ColorRGBA(0.8f, 0, 0.8f, 1);
    protected static final ColorRGBA PROPERTY_VALUE_COLOR = new ColorRGBA(0.9f, 0.4f, 0.9f, 1);
    
    public static final float CONNECTOR_WIDTH = 0.1f;
    public static final float CONNECTOR_OFFSET_W = 1.0f-CONNECTOR_WIDTH;
    public static final float CONNECTOR_LENGTH = 0.2f;
    public static final float CONNECTOR_OFFSET_L = 1.0f-CONNECTOR_LENGTH;
    public static final float CONNECTOR_Z = 1f;
    
    protected HashMap<Vector2i,ColorRGBA> colorMap = new HashMap(); // Color map for TooltipInfo
    
    protected float SIZE;
    protected float size;
    
    protected SpellMatrix matrix;
    protected SpellNodeData data;
    protected Node parent;
    protected Vector3f center;
    protected Vector4f bounds;
    protected Geometry geo;
    protected Geometry[] connections = new Geometry[4];
    protected SinText text;
    
    public SpellNode(Node parent, SpellMatrix matrix, SpellNodeData data, float nodeSize){
        this.parent = parent;
        this.matrix = matrix;
        this.data = data;
        this.center = data.get3DLocation();
        
        this.SIZE = nodeSize;
        this.size = nodeSize/2f;
        
        createGeometry();
        Vector3f offset = Util.getOffset(parent);
        bounds = new Vector4f(offset.x+center.x-size, offset.x+center.x+size, offset.y+center.y-size, offset.y+center.y+size);
    }
    private void createGeometry(){
        if(geo != null){
            geo.removeFromParent();
        }
        geo = GeoFactory.createBox(parent, new Vector3f(size, size, 0f), center, Util.getItemIcon(data.getIcon()));
        if(text != null){
            text.removeFromParent();
        }
        text = GeoFactory.createSinText(parent, size*0.3f, center.add(new Vector3f(0, -size*0.7f, 2f)), "AW32", ColorRGBA.White, SinText.Alignment.Center);
        text.setText(" ");
    }
    
    public SpellNodeData getData(){
        return data;
    }
    public String getType(){
        return data.getType();
    }
    public boolean isEmpty(){
        return true;
    }
    public HashMap<Vector2i,ColorRGBA> getColorMap(){
        return colorMap;
    }
    public String getTooltip(){
        colorMap.clear();
        int start = 0;
        String info = data.getName();
        colorMap.put(new Vector2i(start, info.length()), data.getTypeColor());
        
        if(data.toItem() != null){
            start = info.length();
            info += " Lv"+data.toItem().getItemLevel();
            colorMap.put(new Vector2i(start, info.length()), new ColorRGBA(1, 1, 1, 1));
        }
        
        info += '\n';
        
        start = info.length();
        info += "Position: ";
        colorMap.put(new Vector2i(start, info.length()), new ColorRGBA(0.5f, 0.5f, 0.5f, 1));
        
        start = info.length();
        info += data.getIndex().x+", "+data.getIndex().y;
        colorMap.put(new Vector2i(start, info.length()), ColorRGBA.Orange);
        
        HashMap<String,Float> properties = data.getSpellNodeProperties();
        if(properties.keySet().isEmpty()){
            return info;
        }
        info += "\n";
        for(String key : properties.keySet()){
            info += "\n";
            start = info.length();
            info += key+": ";
            colorMap.put(new Vector2i(start, info.length()), PROPERTY_NAME_COLOR);
            
            start = info.length();
            info += properties.get(key);
            colorMap.put(new Vector2i(start, info.length()), PROPERTY_VALUE_COLOR);
        }
        
        return info;
    }
    
    public void onAction(Vector2f cursorLoc, String bind, boolean down, float tpf){}
    
    public void createConnection(int index, ColorRGBA color){
        if(connections[index] != null){
            connections[index].removeFromParent();
        }
        if(index == 0){ // North
            connections[index] = GeoFactory.createBox(parent, new Vector3f(size*CONNECTOR_WIDTH, size*CONNECTOR_LENGTH, 0), Vector3f.ZERO, color);
            connections[index].setLocalTranslation(new Vector3f(center.x, center.y+(size*CONNECTOR_OFFSET_L), CONNECTOR_Z));
        }else if(index == 1){ // East
            connections[index] = GeoFactory.createBox(parent, new Vector3f(size*CONNECTOR_LENGTH, size*CONNECTOR_WIDTH, 0), Vector3f.ZERO, color);
            connections[index].setLocalTranslation(new Vector3f(center.x+(size*CONNECTOR_OFFSET_L), center.y, CONNECTOR_Z));
        }else if(index == 2){ // South
            connections[index] = GeoFactory.createBox(parent, new Vector3f(size*CONNECTOR_WIDTH, size*CONNECTOR_LENGTH, 0), Vector3f.ZERO, color);
            connections[index].setLocalTranslation(new Vector3f(center.x, center.y-(size*CONNECTOR_OFFSET_L), CONNECTOR_Z));
        }else if(index == 3){ // West
            connections[index] = GeoFactory.createBox(parent, new Vector3f(size*CONNECTOR_LENGTH, size*CONNECTOR_WIDTH, 0), Vector3f.ZERO, color);
            connections[index].setLocalTranslation(new Vector3f(center.x-(size*CONNECTOR_OFFSET_L), center.y, CONNECTOR_Z));
        }
        data.setConnection(index, true);
    }
    public void removeConnection(int index){
        if(connections[index] != null){
            connections[index].removeFromParent();
            connections[index] = null;
        }
        data.setConnection(index, false);
    }
    
    public void calculateConnections(){
        SpellNode[] spellNodes = new SpellNode[4];
        spellNodes[0] = matrix.getSpellNode(data.getX(), data.getY()+1);
        spellNodes[1] = matrix.getSpellNode(data.getX()+1, data.getY());
        spellNodes[2] = matrix.getSpellNode(data.getX(), data.getY()-1);
        spellNodes[3] = matrix.getSpellNode(data.getX()-1, data.getY());
        int i = 0;
        while(i < spellNodes.length){
            if(spellNodes[i] != null){
                if(data.canConnect(spellNodes[i].getData())){
                    ColorRGBA color = ColorRGBA.Red;
                    if(data instanceof ConduitData){
                        ConduitData conduit = (ConduitData) data;
                        color = conduit.getTypeColor();
                    }else if(spellNodes[i].getData() instanceof ConduitData){
                        ConduitData conduit = (ConduitData) spellNodes[i].getData();
                        color = conduit.getTypeColor();
                    }
                    createConnection(i, color);
                    spellNodes[i].createConnection((i+2)%4, color);
                }else{
                    removeConnection(i);
                    spellNodes[i].removeConnection((i+2)%4);
                }
            }
            i++;
        }
    }
    
    public void changeData(SpellNodeData data){
        data.setIndex(this.data.getIndex());
        data.setLocation(this.data.getLocation());
        this.data = data;
        createGeometry();
        calculateConnections();
        matrix.recalculate();
    }
    public void changeData(Item item){
        if(item.getData() instanceof SpellNodeItemData){
            if(data.toItem() != null){
                item.getInventory().add(data.toItem());
            }
            SpellNodeItemData spellNodeItem = (SpellNodeItemData) item.getData();
            changeData(spellNodeItem.getData());
        }
    }
    
    public void recalculate(){
        data.recalculate(matrix);
    }
    
    public void update(float tpf){
        data.update(tpf);
        text.setText(data.getText());
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
