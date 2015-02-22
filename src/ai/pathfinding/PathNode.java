package ai.pathfinding;

/**
 *
 * @author SinisteRing
 */
public class PathNode implements Comparable {
    public PathNode parent;
    private int x;
    private int y;
    private float cost;
    private float heuristic;
    private int depth;
    
    public PathNode(int x, int y){
        this.x = x;
        this.y = y;
    }
    
    public PathNode getParent(){
        return parent;
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public float getCost(){
        return cost;
    }
    
    public void setCost(float cost){
        this.cost = cost;
    }
    public void setDepth(int depth){
        this.depth = depth;
    }
    public void setHeuristicCost(float heuristic){
        this.heuristic = heuristic;
    }
    
    public int setParent(PathNode parent){
        depth = parent.depth+1;
        this.parent = parent;
        return depth;
    }
    
    public int compareTo(Object other){
        PathNode o = (PathNode) other;
        
        float f = heuristic+cost;
        float of = o.heuristic+o.cost;
        
        if(f < of){
            return -1;
        }else if(f > of){
            return 1;
        }else{
            return 0;
        }
    }
}
