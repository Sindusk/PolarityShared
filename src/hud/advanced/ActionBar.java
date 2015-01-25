package hud.advanced;

import character.Player;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import hud.HUDElement;
import tools.GeoFactory;
import ui.Button;

/**
 *
 * @author SinisteRing
 */
public class ActionBar extends HUDElement {
    public static final float GEO_SIZE = 20f;
    
    protected Geometry background;
    protected Button[] actions = new Button[4];
    
    public ActionBar(Node parent, Vector2f loc){
        super(parent, loc);
        background = GeoFactory.createBox(node, new Vector3f(GEO_SIZE*6, GEO_SIZE*1.5f, 0), Vector3f.ZERO, ColorRGBA.Gray);
        int i = 0;
        float offset = -(GEO_SIZE*3.75f);
        while(i < actions.length){
            actions[i] = new Button(node, new Vector2f(offset, 0), GEO_SIZE*2, GEO_SIZE*2, 1);
            actions[i].setColor(ColorRGBA.White);
            actions[i].setText(""+(i+1));
            actions[i].setTextColor(ColorRGBA.Green);
            offset += GEO_SIZE*2.5f;
            i++;
        }
    }
    
    @Override
    public void update(Player p, float tpf){
        //
    }
}
