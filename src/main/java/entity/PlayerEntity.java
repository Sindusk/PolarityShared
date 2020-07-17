package entity;

import character.Player;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import character.types.CharType;
import tools.GeoFactory;

/**
 *
 * @author Sindusk
 */
public class PlayerEntity extends LivingEntity {
    protected Geometry geo;
    protected Geometry point;
    
    public PlayerEntity(Node parent, Player player, ColorRGBA color){
        super(parent, player);
        this.type = CharType.PLAYER;
        // Create the (mock-up) player model
        geo = GeoFactory.createBoxAlpha(modelNode, new Vector3f(0.3f, 0.2f, 1), Vector3f.ZERO, color);
        point = GeoFactory.createBoxAlpha(modelNode, new Vector3f(0.2f, 0.125f, 0.35f), new Vector3f(0.4f, 0f, 0), new ColorRGBA(1, 0, 0, 1));
        //geo = GeoFactory.createBoxAlpha(modelNode, new Vector3f(0.75f, 0.9f, 1), Vector3f.ZERO, Util.getTexture("robot_man"));
        //point = GeoFactory.createBoxAlpha(modelNode, new Vector3f(10f, 0.04f, 0.35f), new Vector3f(10f, 0f, 0), new ColorRGBA(1, 0, 0, 1));
        oldLoc = player.getData().getLocation();
        newLoc = player.getData().getLocation();
        nameplate.setTextColor(new ColorRGBA(0, 1f, 1f, 1));
    }
}
