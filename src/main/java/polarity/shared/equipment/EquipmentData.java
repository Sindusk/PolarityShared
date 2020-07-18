package polarity.shared.equipment;

import com.jme3.math.ColorRGBA;
import com.jme3.network.serializing.Serializable;
import java.util.HashMap;
import polarity.shared.tools.Vector2i;
import polarity.shared.ui.interfaces.TooltipInfo;

/**
 *
 * @author Sindusk
 */
@Serializable
public class EquipmentData implements TooltipInfo{
    protected HashMap<Vector2i,ColorRGBA> colorMap = new HashMap();         // Stores the map of colors for the tooltip
    
    protected String icon;
    
    public EquipmentData(){}
    public EquipmentData(String icon){
        this.icon = icon;
    }
    
    public String getIcon(){
        return icon;
    }
    public HashMap<Vector2i,ColorRGBA> getColorMap(){
        return colorMap;
    }
    public String getTooltip(){
        return "";
        //TBI
    }
}
