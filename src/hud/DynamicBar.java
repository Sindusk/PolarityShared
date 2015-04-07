package hud;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import tools.GeoFactory;
import tools.Util;

/**
 *
 * @author SinisteRing
 */
public class DynamicBar extends HUDElement {
    public static enum Alignment{
        Left, Center, Right;
    }
    protected Alignment align = Alignment.Center;
    protected Vector2f size;
    protected Geometry geo;
    protected float percent = 1;
    
    public DynamicBar(Node parent, Vector2f loc, Vector2f size, ColorRGBA color){
        super(parent, loc);
        this.size = size;
        geo = GeoFactory.createBox(node, new Vector3f(size.x*0.5f, size.y*0.5f, 0), Vector3f.ZERO, color);
    }
    
    public void setAlign(Alignment align){
        if(align == Alignment.Left){
            geo.setLocalTranslation(new Vector3f((percent*(size.x*0.5f))-size.x*0.5f, 0, priority));
        }else if(align == Alignment.Center){
            geo.setLocalTranslation(new Vector3f(0, 0, priority));
        }else if(align == Alignment.Right){
            geo.setLocalTranslation(new Vector3f((-percent*(size.x*0.5f))+size.x*0.5f, 0, priority));
        }
        this.align = align;
    }
    public void setColor(ColorRGBA color){
        geo.setMaterial(Util.getMaterial(color));
    }
    
    public void updateSize(float percent){
        this.percent = percent;
        geo.setLocalScale(percent, 1, 1);
        setAlign(align);
    }
}
