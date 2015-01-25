package spellforge;

import spellforge.nodes.SpellNodeData;
import tools.Util.Vector2i;

/**
 *
 * @author SinisteRing
 */
public class Pulse {
    public Pulse(PulseHandler handler, SpellMatrix matrix, int x, int y){
        if(handler.isChecked(new Vector2i(x, y))){
            return;
        }
        SpellNodeData data = matrix.getSpellNode(x, y).getData();
        handler.setChecked(data);
        if(handler.getOrigin().canProvide(data)){
            handler.addGranted(data);
        }else if(handler.getOrigin().canTravel(data)){
            handler.createPulse(data);
        }
    }
}
