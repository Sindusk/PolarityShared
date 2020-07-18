package polarity.shared.equipment;

import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Sindusk
 */
@Serializable
public class AccessoryData extends EquipmentData {
    public AccessoryData(){}
    public AccessoryData(String icon){
        super(icon);
    }
}
