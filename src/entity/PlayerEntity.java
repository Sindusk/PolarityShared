package entity;

import character.Player;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import netdata.DamageData;
import tools.GeoFactory;
import tools.SinText;
import tools.Sys;

/**
 *
 * @author SinisteRing
 */
public class PlayerEntity extends Entity {
    protected Node nameNode = new Node("Player Name");    // Node that holds the nametag
    protected Player owner;
    protected SinText nameTag;
    protected Geometry nameBackground;  // Transparent background to make the name stand out
    protected Geometry geo;
    protected Geometry point;
    
    public PlayerEntity(Node parent, Player player, ColorRGBA color){
        super(parent);
        owner = player;
        // Create the (mock-up) player model
        geo = GeoFactory.createBox(node, new Vector3f(0.6f, 0.4f, 1), Vector3f.ZERO, color);
        point = GeoFactory.createBox(node, new Vector3f(0.4f, 0.25f, 0.7f), new Vector3f(0.8f, 0f, 0), ColorRGBA.Red);
        // Create the hovering text above the model for the player's nametag
        nameTag = GeoFactory.createSinTextAlpha(nameNode, 1.3f, new Vector3f(0, 2.5f, 2), "AW32", player.getName(), new ColorRGBA(1, 1, 1, 1), SinText.Alignment.Center);
        // Transparent background for the name to stand out
        Vector3f backLoc = new Vector3f(nameTag.getLineWidth()/2f, nameTag.getLineHeight()/2f, 0);
        Vector3f backSize = new Vector3f(0, nameTag.getLocalTranslation().y+(nameTag.getLineHeight()), 1);
        nameBackground = GeoFactory.createBoxAlpha(nameNode, backLoc, backSize, new ColorRGBA(0, 0, 0, 0.7f));
        parent.attachChild(nameNode);
        oldLoc = player.getData().getLocation();
        newLoc = player.getData().getLocation();
    }
    
    @Override
    public void update(float tpf){
        super.update(tpf);
        nameNode.setLocalTranslation(node.getLocalTranslation());
    }
    
    public void damage(float value){
        owner.damage(value);
        Sys.getNetwork().send(new DamageData(owner.getID(), value));
    }
}
