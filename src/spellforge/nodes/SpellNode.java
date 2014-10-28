package spellforge.nodes;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.math.Vector4f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import spellforge.SpellMatrix;
import tools.GeoFactory;
import tools.Util;
import ui.Button;
import ui.Menu;

/**
 *
 * @author SinisteRing
 */
public class SpellNode {
    public static final float SIZE = 4;
    public static final float size = SIZE*0.5f;
    public static final float CONNECTOR_WIDTH = 0.1f;
    public static final float CONNECTOR_OFFSET_W = 1.0f-CONNECTOR_WIDTH;
    public static final float CONNECTOR_LENGTH = 0.2f;
    public static final float CONNECTOR_OFFSET_L = 1.0f-CONNECTOR_LENGTH;
    
    protected SpellMatrix matrix;
    protected SpellNodeData data;
    protected Node parent;
    protected Vector3f center;
    protected Vector4f bounds;
    protected Geometry geo;
    protected Geometry[] connections = new Geometry[4];
    
    private void createGeometry(String icon){
        if(geo != null){
            geo.removeFromParent();
        }
        geo = GeoFactory.createBox(parent, new Vector3f(size, size, 0f), center, Util.getSpellNodePath(icon));
    }
    public SpellNode(Node parent, SpellMatrix matrix, SpellNodeData data){
        this.parent = parent;
        this.matrix = matrix;
        this.data = data;
        this.center = data.get3DLocation();
        createGeometry("empty");
        Vector3f offset = Util.getOffset(parent);
        bounds = new Vector4f(offset.x+center.x-size, offset.x+center.x+size, offset.y+center.y-size, offset.y+center.y+size);
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
    public String getTooltip(){
        return data.getType()+"\nPosition: "+data.getX()+", "+data.getY();
    }
    
    public void onAction(Vector2f cursorLoc, String bind, boolean down, float tpf){
        //
    }
    
    public void createConnection(int index){
        if(connections[index] != null){
            connections[index].removeFromParent();
        }
        if(index == 0){ // North
            connections[index] = GeoFactory.createBox(parent, new Vector3f(size*CONNECTOR_WIDTH, size*CONNECTOR_LENGTH, 0), Vector3f.ZERO, ColorRGBA.Red);
            connections[index].setLocalTranslation(new Vector3f(center.x, center.y+(size*CONNECTOR_OFFSET_L), 0.5f));
        }else if(index == 1){ // East
            connections[index] = GeoFactory.createBox(parent, new Vector3f(size*CONNECTOR_LENGTH, size*CONNECTOR_WIDTH, 0), Vector3f.ZERO, ColorRGBA.Red);
            connections[index].setLocalTranslation(new Vector3f(center.x+(size*CONNECTOR_OFFSET_L), center.y, 0.5f));
        }else if(index == 2){ // South
            connections[index] = GeoFactory.createBox(parent, new Vector3f(size*CONNECTOR_WIDTH, size*CONNECTOR_LENGTH, 0), Vector3f.ZERO, ColorRGBA.Red);
            connections[index].setLocalTranslation(new Vector3f(center.x, center.y-(size*CONNECTOR_OFFSET_L), 0.5f));
        }else if(index == 3){ // West
            connections[index] = GeoFactory.createBox(parent, new Vector3f(size*CONNECTOR_LENGTH, size*CONNECTOR_WIDTH, 0), Vector3f.ZERO, ColorRGBA.Red);
            connections[index].setLocalTranslation(new Vector3f(center.x-(size*CONNECTOR_OFFSET_L), center.y, 0.5f));
        }
    }
    public void removeConnection(int index){
        if(connections[index] != null){
            connections[index].removeFromParent();
            connections[index] = null;
        }
    }
    
    public void calculateConnections(){
        SpellNode[] spellNodes = new SpellNode[4];
        spellNodes[0] = matrix.getSpellNode(data.getX(), data.getY()+1);
        spellNodes[1] = matrix.getSpellNode(data.getX()+1, data.getY());
        spellNodes[2] = matrix.getSpellNode(data.getX(), data.getY()-1);
        spellNodes[3] = matrix.getSpellNode(data.getX()-1, data.getY());
        int i = 0;
        while(i < spellNodes.length){
            if(data.canConnect(spellNodes[i].getData())){
                createConnection(i);
                spellNodes[i].createConnection((i+2)%4);
            }else{
                removeConnection(i);
                spellNodes[i].removeConnection((i+2)%4);
            }
            i++;
        }
    }
    
    public Button addGeneratorOption(Menu menu){
        Button b = new Button(menu.getNode(), new Vector2f(0, -menu.size()*20), 200, 20, 1){
            @Override
            public void onAction(Vector2f cursorLoc, String bind, boolean down, float tpf){
                data = new EnergyGenData(data);
                createGeometry("generator");
                calculateConnections();
            }
        };
        b.setColor(new ColorRGBA(0, 0.5f, 0, 1));
        b.setText("Generator");
        b.setTextColor(ColorRGBA.White);
        return b;
    }
    
    public Button addPowerConduitOption(Menu menu){
        Button b = new Button(menu.getNode(), new Vector2f(0, -menu.size()*20), 200, 20, 1){
            @Override
            public void onAction(Vector2f cursorLoc, String bind, boolean down, float tpf){
                data = new PowerConduitData(data);
                createGeometry("powerConduit");
                calculateConnections();
            }
        };
        b.setColor(new ColorRGBA(0.5f, 0, 0, 1));
        b.setText("Power Conduit");
        b.setTextColor(ColorRGBA.White);
        return b;
    }
    
    public Button addProjectileOption(Menu menu){
        Button b = new Button(menu.getNode(), new Vector2f(0, -menu.size()*20), 200, 20, 1){
            @Override
            public void onAction(Vector2f cursorLoc, String bind, boolean down, float tpf){
                data = new ProjectileCoreData(data);
                createGeometry("projectile");
                calculateConnections();
            }
        };
        b.setColor(new ColorRGBA(0, 0.6f, 0.6f, 1));
        b.setText("Projectile");
        b.setTextColor(ColorRGBA.White);
        return b;
    }
    public Button addModifierConduitOption(Menu menu){
        Button b = new Button(menu.getNode(), new Vector2f(0, -menu.size()*20), 200, 20, 1){
            @Override
            public void onAction(Vector2f cursorLoc, String bind, boolean down, float tpf){
                data = new ModifierConduitData(data);
                createGeometry("modifierConduit");
                calculateConnections();
            }
        };
        b.setColor(new ColorRGBA(0, 0, 0.5f, 1));
        b.setText("Modifier Conduit");
        b.setTextColor(ColorRGBA.White);
        return b;
    }
    public Button addDamageModifierOption(Menu menu){
        Button b = new Button(menu.getNode(), new Vector2f(0, -menu.size()*20), 200, 20, 1){
            @Override
            public void onAction(Vector2f cursorLoc, String bind, boolean down, float tpf){
                data = new DamageModifierData(data);
                createGeometry("modifier");
                calculateConnections();
            }
        };
        b.setColor(new ColorRGBA(0, 0, 1f, 1));
        b.setText("Modifier");
        b.setTextColor(ColorRGBA.White);
        return b;
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
