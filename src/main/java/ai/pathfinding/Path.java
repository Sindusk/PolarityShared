package ai.pathfinding;

import java.util.ArrayList;
import tools.Vector2i;

/**
 *
 * @author SinisteRing
 */
public class Path {
    private ArrayList<Vector2i> steps = new ArrayList();
    
    public Path(){}
    
    public int getLength(){
        return steps.size();
    }
    
    public Vector2i getStep(int index){
        return steps.get(index);
    }
    
    public int getX(int index){
        return getStep(index).x;
    }
    public int getY(int index){
        return getStep(index).y;
    }
    
    public void appendStep(int x, int y){
        steps.add(new Vector2i(x, y));
    }
    public void appendStep(Vector2i step){
        steps.add(step);
    }
    
    public void prependStep(int x, int y){
        steps.add(0, new Vector2i(x, y));
    }
    public void prependStep(Vector2i step){
        steps.add(0, step);
    }
    
    public boolean contains(int x, int y){
        return steps.contains(new Vector2i(x, y));
    }
    public boolean contains(Vector2i step){
        return steps.contains(step);
    }
    
    @Override
    public String toString(){
        String s = "";
        for(Vector2i step : steps){
            s += step.toString();
            s += " : ";
        }
        return s;
    }
}
