package polarity.shared.ai.pathfinding;

import polarity.shared.entity.Entity;
import polarity.shared.world.interfaces.TileBasedMap;

/**
 *
 * @author SinisteRing
 */
public interface AStarHeuristic {
    public float getCost(TileBasedMap map, Entity entity, int x, int y, int tx, int ty);
}
