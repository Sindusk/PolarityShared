package entity;

import character.Player;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import tools.GeoFactory;
import tools.SinText;

/**
 *
 * @author SinisteRing
 */
public class PlayerEntity extends Entity {
    protected Node nameNode = new Node("Player Name");    // Node that holds the nametag
    protected SinText nameTag;
    protected Geometry geo;
    protected Geometry point;
    
    public PlayerEntity(Node parent, Player player, ColorRGBA color){
        super(parent);
        // Create the (mock-up) player model
        geo = GeoFactory.createBox(node, new Vector3f(0.7f, 0.5f, 1), Vector3f.ZERO, color);
        point = GeoFactory.createBox(node, new Vector3f(0.5f, 0.3f, 0.7f), new Vector3f(1f, 0f, 0), ColorRGBA.Red);
        // Create the hovering text above the model for the player's nametag
        nameTag = GeoFactory.createSinTextAlpha(nameNode, 1.5f, new Vector3f(0, 2.5f, 2), "TNR32", player.getName(), ColorRGBA.White, SinText.Alignment.Center);
        parent.attachChild(nameNode);
    }
    
    @Override
    public void update(float tpf){
        super.update(tpf);
        nameNode.setLocalTranslation(node.getLocalTranslation());
    }
}
