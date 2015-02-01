package items;

import com.jme3.math.ColorRGBA;
import com.jme3.network.serializing.Serializable;
import spellforge.nodes.SpellNodeData;

/**
 *
 * @author SinisteRing
 */
@Serializable
public class SpellNodeItem extends Item {
    protected SpellNodeData data;
    
    public SpellNodeItem(Inventory inv, int itemLevel, SpellNodeData data){
        super(inv, itemLevel, data.getIcon());
        this.data = data;
        this.name = data.getName();
        this.archetype = "Spell Node";
        this.archetypeColor = ColorRGBA.Red;
        this.type = data.getType();
        this.typeColor = data.getTypeColor();
        this.properties = data.genProperties(itemLevel);
    }
    public SpellNodeData getData(){
        data.setItem(this);
        return data;
    }
}
