package polarity.shared.ui.interfaces;

import com.jme3.math.ColorRGBA;
import java.util.HashMap;
import polarity.shared.tools.Vector2i;

/**
 *
 * @author SinisteRing
 */
public interface TooltipInfo {
    public HashMap<Vector2i,ColorRGBA> getColorMap();
    public String getTooltip();
}
