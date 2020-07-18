package polarity.shared.ai.pathfinding;

import polarity.shared.entity.Entity;
import polarity.shared.world.interfaces.TileBasedMap;

/**
 *
 * @author SinisteRing
 */
public class ClosestHeuristic implements AStarHeuristic {
    public float getCost(TileBasedMap map, Entity entity, int x, int y, int tx, int ty){
        float dx = tx - x;
        float dy = ty - y;
        
        float result = (float) (Math.sqrt((dx*dx)+(dy*dy)));
        
        return result;
    }
}
