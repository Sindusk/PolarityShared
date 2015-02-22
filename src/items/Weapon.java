package items;

import com.jme3.network.serializing.Serializable;

/**
 *
 * @author SinisteRing
 */
@Serializable
public class Weapon extends ItemData {
    protected float speed = 10f;
    
    public Weapon(){}
    
    public Weapon(Inventory inv, int itemLevel, String icon){
        super(inv, itemLevel, icon);
    }
    
    public float getSpeed(){
        return speed;
    }
}
