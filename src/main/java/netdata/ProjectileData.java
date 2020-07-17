package netdata;

import com.jme3.math.Vector2f;
import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;
import character.types.Owner;
import spellforge.nodes.CoreVals;

/**
 *
 * @author SinisteRing
 */
@Serializable
public class ProjectileData extends AbstractMessage {
    protected int hashCode;
    protected Owner owner;
    protected Vector2f start;
    protected Vector2f target;
    protected CoreVals values;
    public ProjectileData() {}
    public ProjectileData(int hashCode, Owner owner, Vector2f start, Vector2f target, CoreVals values){
        this.hashCode = hashCode;
        this.owner = owner;
        this.start = start;
        this.target = target;
        this.values = values;
    }
    public int getHashCode(){
        return hashCode;
    }
    public Owner getOwner(){
        return owner;
    }
    public Vector2f getStart(){
        return start;
    }
    public Vector2f getTarget(){
        return target;
    }
    public CoreVals getValues(){
        return values;
    }
}
