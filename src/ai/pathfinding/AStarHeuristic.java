package ai.pathfinding;

import entity.Entity;
import world.interfaces.TileBasedMap;

/**
 *
 * @author SinisteRing
 */
public interface AStarHeuristic {
    public float getCost(TileBasedMap map, Entity entity, int x, int y, int tx, int ty);
}
