package world.interfaces;

import entity.Entity;

/**
 *
 * @author SinisteRing
 */
public interface TileBasedMap {
    public int getWidthInTiles();
    public int getHeightInTiles();
    public int getXOffset();
    public int getYOffset();
    public void pathFinderVisited(int x, int y);
    public boolean blocked(Entity entity, int x, int y);
    public float getCost(Entity entity, int sx, int sy, int tx, int ty);
}
