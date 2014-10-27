package spellforge.nodes;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.math.Vector4f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
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
    
    protected SpellNodeData data;
    protected Node parent;
    protected Vector3f center;
    protected Vector4f bounds;
    protected Geometry geo;
    
    private void createGeometry(String icon){
        if(geo != null){
            geo.removeFromParent();
        }
        geo = GeoFactory.createBox(parent, new Vector3f(size, size, 0f), center, Util.getSpellNodePath(icon));
    }
    public SpellNode(Node parent, SpellNodeData data){
        this.parent = parent;
        this.data = data;
        this.center = data.get3DLocation();
        createGeometry("empty");
        Vector3f offset = Util.getOffset(parent);
        bounds = new Vector4f(offset.x+center.x-size, offset.x+center.x+size, offset.y+center.y-size, offset.y+center.y+size);
    }
    
    public SpellNodeData getData(){
        return data;
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
    
    public Button addGeneratorOption(Menu menu){
        Button b = new Button(menu.getNode(), new Vector2f(0, -menu.size()*20), 200, 20, 1){
            @Override
            public void onAction(Vector2f cursorLoc, String bind, boolean down, float tpf){
                data = new EnergyGenData(data);
                createGeometry("generator");
            }
        };
        b.setColor(new ColorRGBA(0, 0.5f, 0, 1));
        b.setText("New Generator");
        b.setTextColor(ColorRGBA.White);
        return b;
    }
    
    public Button addPowerConduitOption(Menu menu){
        Button b = new Button(menu.getNode(), new Vector2f(0, -menu.size()*20), 200, 20, 1){
            @Override
            public void onAction(Vector2f cursorLoc, String bind, boolean down, float tpf){
                createGeometry("powerConduit");
            }
        };
        b.setColor(new ColorRGBA(0.5f, 0, 0, 1));
        b.setText("New Power Conduit");
        b.setTextColor(ColorRGBA.White);
        return b;
    }
    
    public Button addProjectileOption(Menu menu){
        Button b = new Button(menu.getNode(), new Vector2f(0, -menu.size()*20), 200, 20, 1){
            @Override
            public void onAction(Vector2f cursorLoc, String bind, boolean down, float tpf){
                createGeometry("projectile");
            }
        };
        b.setColor(new ColorRGBA(0, 0.6f, 0.6f, 1));
        b.setText("New Projectile");
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
