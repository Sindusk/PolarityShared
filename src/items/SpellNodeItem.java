package items;

import com.jme3.network.serializing.Serializable;

/**
 *
 * @author SinisteRing
 */
@Serializable
public class SpellNodeItem extends Item {
    public SpellNodeItem(String icon, String type){
        super(icon);
        this.archtype = "Spell Node";
        this.type = type;
        if(type.equals("Generator")){
            addProperty("Power Rate", 5);
            addProperty("Maximum Power", 100);
        }else if(type.equals("Core")){
            addProperty("Cost", 10);
        }
    }
}
