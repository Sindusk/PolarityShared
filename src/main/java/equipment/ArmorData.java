package equipment;

import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Sindusk
 */
@Serializable
public class ArmorData extends EquipmentData {
    public ArmorData(){}
    public ArmorData(String icon){
        super(icon);
    }
}
