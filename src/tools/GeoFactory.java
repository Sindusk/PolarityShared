package tools;

import com.jme3.font.BitmapText;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Cylinder;
import com.jme3.scene.shape.Line;
import com.jme3.scene.shape.Quad;
import com.jme3.scene.shape.Sphere;
import tools.SinText.Alignment;

/**
 * CG (Create Geometry) - Used to create geometries of all types without multiple lines of code.
 * @author SinisteRing
 */
public class GeoFactory {
    // BitmapText:
    public static BitmapText createText(Node node, float size, Vector3f trans, String font, ColorRGBA color){
        BitmapText text = new BitmapText(T.getFont(S.getAssetManager(), font));
        text.setColor(color);
        text.setSize(size);
        text.setLocalTranslation(trans);
        node.attachChild(text);
        return text;
    }
    public static SinText createSinText(Node node, float size, Vector3f trans, String font, String text, ColorRGBA color, Alignment align){
        SinText txt = new SinText(T.getFont(S.getAssetManager(), font));
        txt.setColor(color);
        txt.setSize(size);
        txt.setLocalTranslation(trans);
        txt.setText(text);
        txt.setAlignment(align);
        node.attachChild(txt.getNode());
        return txt;
    }

    // --- Boxes --- //
    // Color:
    public static Geometry createBox(Node node, String name, Vector3f size, Vector3f trans, ColorRGBA color){
        Box b = new Box(Vector3f.ZERO, size.getX(), size.getY(), size.getZ());
        Geometry g = new Geometry(name, b);
        Material m = T.getMaterial(S.getAssetManager(), color);
        g.setMaterial(m);
        g.setLocalTranslation(trans);
        if(node != null) {
            node.attachChild(g);
        }
        return g;
    }
    public static Geometry createBox(Node node, Vector3f size, Vector3f trans, ColorRGBA color){
        return createBox(node, "", size, trans, color);
    }
    // Texture:
    public static Geometry createBox(Node node, String name, Vector3f size, Vector3f trans, String tex, Vector2f scale){
        Box b = new Box(Vector3f.ZERO, size.getX(), size.getY(), size.getZ());
        b.scaleTextureCoordinates(scale);
        Geometry g = new Geometry(name, b);
        Material m = T.getMaterial(S.getAssetManager(), tex);
        g.setMaterial(m);
        g.setLocalTranslation(trans);
        if(node != null) {
            node.attachChild(g);
        }
        return g;
    }
    public static Geometry createBox(Node node, String name, Vector3f size, Vector3f trans, String tex){
        return createBox(node, name, size, trans, tex, new Vector2f(1, 1));
    }
    public static Geometry createBox(Node node, Vector3f size, Vector3f trans, String tex){
        return createBox(node, "", size, trans, tex, new Vector2f(1, 1));
    }

    // Cylinders:
    public static Geometry createCylinder(Node node, String name, float radius, float length, Vector3f trans, ColorRGBA color){
        Cylinder b = new Cylinder(16, 16, radius, length, true);
        Geometry g = new Geometry(name, b);
        Material m = T.getMaterial(S.getAssetManager(), color);
        g.setMaterial(m);
        g.setLocalTranslation(trans);
        if(node != null) {
            node.attachChild(g);
        }
        return g;
    }
    public static Geometry createCylinder(Node node, String name, float radius, float length, Vector3f trans, String tex, Vector2f scale){
        Cylinder b = new Cylinder(16, 16, radius, length, true);
        b.scaleTextureCoordinates(scale);
        Geometry g = new Geometry(name, b);
        Material m = T.getMaterial(S.getAssetManager(), tex);
        g.setMaterial(m);
        g.setLocalTranslation(trans);
        if(node != null) {
            node.attachChild(g);
        }
        return g;
    }

    // Lines:
    public static Geometry createLine(Node node, String name, float width, Vector3f start, Vector3f stop, ColorRGBA color){
        Line b = new Line(start, stop);
        b.setLineWidth(width);
        Geometry g = new Geometry(name, b);
        Material m = T.getMaterial(S.getAssetManager(), color);
        g.setMaterial(m);
        if(node != null) {
            node.attachChild(g);
        }
        return g;
    }
    
    // Quads:
    public static Geometry createQuad(Node node, String name, float width, float height, Vector3f trans, ColorRGBA color){
        Quad b = new Quad(width, height);
        Geometry g = new Geometry(name, b);
        Material m = T.getMaterial(S.getAssetManager(), color);
        g.setMaterial(m);
        if(node != null){
            node.attachChild(g);
        }
        return g;
    }
    
    // Spheres:
    public static Geometry createSphere(Node node, String name, float radius, Vector3f trans, ColorRGBA color){
        Sphere b = new Sphere(16, 16, radius);
        Geometry g = new Geometry(name, b);
        Material m = T.getMaterial(S.getAssetManager(), color);
        g.setMaterial(m);
        g.setLocalTranslation(trans);
        if(node != null) {
            node.attachChild(g);
        }
        return g;
    }
    public static Geometry createSphere(Node node, String name, float radius, Vector3f trans, String tex, Sphere.TextureMode mode){
        Sphere b = new Sphere(16, 16, radius);
        b.setTextureMode(mode);
        Geometry g = new Geometry(name, b);
        Material m = T.getMaterial(S.getAssetManager(), tex);
        g.setMaterial(m);
        g.setLocalTranslation(trans);
        if(node != null) {
            node.attachChild(g);
        }
        return g;
    }
}
