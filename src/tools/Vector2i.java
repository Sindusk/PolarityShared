package tools;

import com.jme3.network.serializing.Serializable;

/**
 *
 * @author SinisteRing
 */
@Serializable
public class Vector2i{
    public int x;
    public int y;

    public Vector2i(){} // For serialization
    public Vector2i(int x, int y){
        this.x = x;
        this.y = y;
    }

    public void addLocal(Vector2i other){
        this.x += other.x;
        this.y += other.y;
    }
    public Vector2i add(Vector2i other){
        return new Vector2i(x+other.x, y+other.y);
    }
    public Vector2i add(int x, int y){
        return new Vector2i(this.x+x, this.y+y);
    }

    public boolean equalsInverted(Vector2i other){
        if(other.x != -this.x){
            return false;
        }else if(other.y != -this.y){
            return false;
        }
        return true;
    }

    @Override
    public boolean equals(Object o){
        if (!(o instanceof Vector2i)) {
            return false;
        }
        Vector2i other = (Vector2i) o;
        if(this == other){
            return true;
        }
        if(other.x != this.x){
            return false;
        }
        if(other.y != this.y){
            return false;
        }
        return true;
    }

    public boolean within(Vector2i other, int distance){
        if(Math.abs(other.x-this.x) <= distance && Math.abs(other.y-this.y) <= distance){
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + this.x;
        hash = 29 * hash + this.y;
        return hash;
    }

    public Vector2i invert(){
        return new Vector2i(-x, -y);
    }

    @Override
    public Vector2i clone(){
        return new Vector2i(x, y);
    }
    @Override
    public String toString(){
        return "("+x+", "+y+")";
    }
}
