package ui.interfaces;

import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;

/**
 *
 * @author SinisteRing
 */
public interface Draggable {
    public Vector3f getOffset();
    public void moveWithOffset(Vector2f cursorLoc);
    public void resetDragging();
}
