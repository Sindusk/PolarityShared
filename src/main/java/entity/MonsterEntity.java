package entity;

import character.Monster;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import character.types.CharType;
import tools.GeoFactory;

/**
 *
 * @author SinisteRing
 */
public class MonsterEntity extends LivingEntity {
    protected Geometry geo;
    protected Geometry point;
    
    public MonsterEntity(Node parent, Monster owner){
        super(parent, owner);
        this.type = CharType.MONSTER;
        geo = GeoFactory.createBox(modelNode, new Vector3f(0.3f, 0.2f, 1), Vector3f.ZERO, new ColorRGBA(1, 0, 1, 1));
        point = GeoFactory.createBox(modelNode, new Vector3f(0.2f, 0.125f, 0.35f), new Vector3f(0.4f, 0f, 0), ColorRGBA.Red);
    }
}
