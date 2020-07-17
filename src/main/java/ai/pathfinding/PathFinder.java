package ai.pathfinding;

import entity.Entity;
import tools.Vector2i;

/**
 *
 * @author SinisteRing
 */
public interface PathFinder {
    public Path findPath(Entity entity, Vector2i start, Vector2i target);
}
