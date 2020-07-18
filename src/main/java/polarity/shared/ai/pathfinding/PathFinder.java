package polarity.shared.ai.pathfinding;

import polarity.shared.entity.Entity;
import polarity.shared.tools.Vector2i;

/**
 *
 * @author SinisteRing
 */
public interface PathFinder {
    public Path findPath(Entity entity, Vector2i start, Vector2i target);
}
