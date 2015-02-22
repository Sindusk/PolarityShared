package ai.pathfinding;

import entity.Entity;
import java.util.ArrayList;
import tools.Util;
import world.interfaces.TileBasedMap;

/**
 *
 * @author SinisteRing
 */
public class AStarPathFinder {
    private ArrayList closed = new ArrayList();
    private SortedList open = new SortedList();
    
    private TileBasedMap map;
    private int maxSearchDistance;
    private int xOffset;
    private int yOffset;
    
    private PathNode[][] nodes;
    private boolean allowDiagMovement;
    private AStarHeuristic heuristic;
    
    public AStarPathFinder(TileBasedMap map, int maxSearchDistance, boolean allowDiagMovement, AStarHeuristic heuristic){
        this.heuristic = heuristic;
        this.map = map;
        this.maxSearchDistance = maxSearchDistance;
        this.allowDiagMovement = allowDiagMovement;
        
        nodes = new PathNode[map.getWidthInTiles()+1][map.getHeightInTiles()+1];
        xOffset = map.getXOffset();
        yOffset = map.getYOffset();
        
        int x = 0;
        int y;
        while(x < map.getWidthInTiles()){
            y = 0;
            while(y < map.getHeightInTiles()){
                nodes[x][y] = new PathNode(x+xOffset, y+yOffset);
                y++;
            }
            x++;
        }
    }
    public AStarPathFinder(TileBasedMap map, int maxSearchDistance, boolean allowDiagMovement){
        this(map, maxSearchDistance, allowDiagMovement, new ClosestHeuristic());
    }
    
    public Path findPath(Entity entity, int sx, int sy, int tx, int ty){
        if(sx < 0){
            sx--;
        }
        if(sy < 0){
            sy--;
        }
        if(tx < 0){
            tx--;
        }
        if(ty < 0){
            ty--;
        }
        
        int sxOff = sx-xOffset;
        int syOff = sy-yOffset;
        int txOff = tx-xOffset;
        int tyOff = ty-yOffset;
        
        if(map.blocked(entity, txOff, tyOff)){
            Util.log("Target is blocked!");
            return null;
        }
        
        nodes[sxOff][syOff].setCost(0);
        nodes[sxOff][syOff].setDepth(0);
        closed.clear();
        open.clear();
        open.add(nodes[sxOff][syOff]);
        
        nodes[txOff][tyOff].parent = null;
        
        int maxDepth = 0;
        while((maxDepth < maxSearchDistance) && (open.size() != 0)){
            PathNode current = getFirstInOpen();
            if(current == nodes[txOff][tyOff]){
                break;
            }
            
            removeFromOpen(current);
            addToClosed(current);
            
            int x = -1;
            int y;
            while(x < 2){
                y = -1;
                while(y < 2){
                    if((x == 0) && (y == 0)){
                        y++;
                        continue;
                    }
                    
                    if(!allowDiagMovement){
                        if((x != 0) && (y != 0)){
                            y++;
                            continue;
                        }
                    }
                    
                    int xp = x + current.getX()-xOffset;
                    int yp = y + current.getY()-yOffset;
                    
                    if(isValidLocation(entity, sx, sy, xp, yp)){
                        float nextStepCost = current.getCost() + getMovementCost(entity, current.getX(), current.getY(), xp, yp);
                        PathNode neighbour = nodes[xp][yp];
                        map.pathFinderVisited(xp, yp);
                        
                        if(nextStepCost < neighbour.getCost()){
                            if(inOpenList(neighbour)){
                                removeFromOpen(neighbour);
                            }
                            if(inClosedList(neighbour)){
                                removeFromClosed(neighbour);
                            }
                        }
                        
                        if(!inOpenList(neighbour) && !(inClosedList(neighbour))){
                            neighbour.setCost(nextStepCost);
                            neighbour.setHeuristicCost(getHeuristicCost(entity, xp, yp, txOff, tyOff));
                            maxDepth = Math.max(maxDepth, neighbour.setParent(current));
                            addToOpen(neighbour);
                        }
                    }
                    y++;
                }
                x++;
            }
        }
        
        if(nodes[txOff][tyOff].getParent() == null){
            return null;
        }
        
        Path path = new Path();
        PathNode target = nodes[txOff][tyOff];
        while(target != nodes[sxOff][syOff]){
            path.prependStep(target.getX(), target.getY());
            target = target.getParent();
        }
        path.prependStep(sx, sy);
        
        return path;
    }
    
    protected PathNode getFirstInOpen(){
        return (PathNode) open.first();
    }
    protected void addToOpen(PathNode node){
        open.add(node);
    }
    protected boolean inOpenList(PathNode node){
        return open.contains(node);
    }
    protected void removeFromOpen(PathNode node){
        open.remove(node);
    }
    protected void addToClosed(PathNode node){
        closed.add(node);
    }
    protected boolean inClosedList(PathNode node){
        return closed.contains(node);
    }
    protected void removeFromClosed(PathNode node){
        closed.remove(node);
    }
    protected boolean isValidLocation(Entity entity, int sx, int sy, int x, int y){
        boolean invalid = (x < 0) || (y < 0) || (x >= map.getWidthInTiles()) || (y >= map.getHeightInTiles());
        
        if((!invalid) && ((sx != x) || (sy != y))){
            invalid = map.blocked(entity, x, y);
        }
        
        return !invalid;
    }
    public float getMovementCost(Entity entity, int sx, int sy, int tx, int ty){
        return map.getCost(entity, sx, sy, tx, ty);
    }
    public float getHeuristicCost(Entity entity, int x, int y, int tx, int ty){
        return heuristic.getCost(map, entity, x, y, tx, ty);
    }
}
