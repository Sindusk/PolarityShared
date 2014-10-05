package tools;

import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue.Bucket;
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
    /**
     * Creates a text geometry.
     * @param node Parent node - this is the node that the text will attach to.
     * @param size Size of the text vertically.
     * @param trans Translation (position) of the text in relation to parent node.
     * @param font Font to use.
     * @param text Initial text. Is able to be changed dynamically.
     * @param color Initial color. Is able to be changed dynamically.
     * @param align SinText.Alignment options: Left, Center, Right. Will function from the center point (trans)
     * @return Returns the resulting text geometry.
     */
    public static SinText createSinText(Node node, float size, Vector3f trans, String font, String text, ColorRGBA color, Alignment align){
        SinText txt = new SinText(Util.getFont(Sys.getAssetManager(), font));
        txt.setColor(color);
        txt.setSize(size);
        txt.setLocalTranslation(trans);
        txt.setText(text);
        txt.setAlignment(align);
        node.attachChild(txt.getNode());
        return txt;
    }
    /**
     * Same as createSinText, but makes background transparent.
     * <p>
     * Mainly used for text placed in the world. GUI text does not need this.
     */
    public static SinText createSinTextAlpha(Node node, float size, Vector3f trans, String font, String text, ColorRGBA color, Alignment align){
        SinText txt = createSinText(node, size, trans, font, text, color, align);
        txt.setQueueBucket(Bucket.Transparent);
        return txt;
    }

    /**
     * Creates a Box geometry (solid color version)
     * @param node Parent node - this is the node that the geometry will attach to.
     * @param name Name of the geometry - Useful for organization, but otherwise not very useful.
     * @param size Size of the geometry. Keep in mind that the resulting size is twice the input value.
     * The Geometry is generated from the center and extends to (input value) in each direction along that axis.
     * @param trans Position of the geometry in 3D space. Note that this is affected by the location of the parent node.
     * @param color ColorRGBA for the color of the sphere. Use new ColorRGBA(Red,Green,Blue,Alpha) or a ColorRGBA static field.
     * @return Returns the resulting geometry.
     */
    public static Geometry createBox(Node node, String name, Vector3f size, Vector3f trans, ColorRGBA color){
        Box b = new Box(Vector3f.ZERO, size.getX(), size.getY(), size.getZ());
        Geometry g = new Geometry(name, b);
        Material m = Util.getMaterial(Sys.getAssetManager(), color);
        g.setMaterial(m);
        g.setLocalTranslation(trans);
        if(node != null) {
            node.attachChild(g);
        }
        return g;
    }
    /**
     * @see GeoFactory.createBox(Node node, String name, Vector3f size, Vector3f trans, ColorRGBA color)
     */
    public static Geometry createBox(Node node, Vector3f size, Vector3f trans, ColorRGBA color){
        return createBox(node, " ", size, trans, color);
    }
    
    /**
     * Creates a Box geometry (solid color version, transparent)
     * @param node Parent node - this is the node that the geometry will attach to.
     * @param name Name of the geometry - Useful for organization, but otherwise not very useful.
     * @param size Size of the geometry. Keep in mind that the resulting size is twice the input value.
     * The Geometry is generated from the center and extends to (input value) in each direction along that axis.
     * @param trans Position of the geometry in 3D space. Note that this is affected by the location of the parent node.
     * @param color ColorRGBA for the color of the sphere. Use new ColorRGBA(Red,Green,Blue,Alpha) or a ColorRGBA static field.
     * @return Returns the resulting geometry.
     */
    public static Geometry createBoxAlpha(Node node, String name, Vector3f size, Vector3f trans, ColorRGBA color){
        Geometry g = createBox(node, name, size, trans, color);
        g.setQueueBucket(Bucket.Transparent);
        return g;
    }
    /**
     * @see GeoFactory.createBox(Node node, String name, Vector3f size, Vector3f trans, ColorRGBA color)
     */
    public static Geometry createBoxAlpha(Node node, Vector3f size, Vector3f trans, ColorRGBA color){
        return createBoxAlpha(node, " ", size, trans, color);
    }
    
    /**
     * Creates a Box geometry (textured version)
     * @param node Parent node - this is the node that the geometry will attach to.
     * @param name Name of the geometry - Useful for organization, but otherwise not very useful.
     * @param size Size of the geometry. Keep in mind that the resulting size is twice the input value.
     * The Geometry is generated from the center and extends to (input value) in each direction along that axis.
     * @param trans Position of the geometry in 3D space. Note that this is affected by the location of the parent node.
     * @param tex Path to the texture used for the geometry.
     * @param scale Texture scaling for each face of the geometry.
     * For example, new Vector2f(3, 2) will apply the texture 6 times in a grid, with 3 columns and 2 rows.
     * @return Returns the resulting geometry.
     */
    public static Geometry createBox(Node node, String name, Vector3f size, Vector3f trans, String tex, Vector2f scale){
        Box b = new Box(Vector3f.ZERO, size.getX(), size.getY(), size.getZ());
        b.scaleTextureCoordinates(scale);
        Geometry g = new Geometry(name, b);
        Material m = Util.getMaterial(Sys.getAssetManager(), tex);
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
        Material m = Util.getMaterial(Sys.getAssetManager(), color);
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
        Material m = Util.getMaterial(Sys.getAssetManager(), tex);
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
        Material m = Util.getMaterial(Sys.getAssetManager(), color);
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
        Material m = Util.getMaterial(Sys.getAssetManager(), color);
        g.setMaterial(m);
        if(node != null){
            node.attachChild(g);
        }
        return g;
    }
    
    /**
     * Creates a Sphere geometry (solid color version).
     * <p>
     * 
     * @param node Parent node - this is the node that the geometry will attach to.
     * @param name Name of the geometry - Useful for organization, but otherwise not very useful.
     * @param radius Radius of the sphere.
     * @param trans Position of the geometry in 3D space. Note that this is affected by the location of the parent node.
     * @param color ColorRGBA for the color of the sphere. Use new ColorRGBA(Red,Green,Blue,Alpha) or a ColorRGBA static field.
     * @return Returns the resulting geometry.
     */
    public static Geometry createSphere(Node node, String name, float radius, Vector3f trans, ColorRGBA color){
        Sphere b = new Sphere(16, 16, radius);
        Geometry g = new Geometry(name, b);
        Material m = Util.getMaterial(Sys.getAssetManager(), color);
        g.setMaterial(m);
        g.setLocalTranslation(trans);
        if(node != null) {
            node.attachChild(g);
        }
        return g;
    }
    /**
     * Creates a Sphere geometry (textured version).
     * <p>
     * Used as a way to shorten the amount of time necessary for creating a textured Sphere.
     * @param node Parent node - this is the node that the geometry will attach to.
     * @param name Name of the geometry - Useful for organization, but otherwise not very useful.
     * @param radius Radius of the sphere.
     * @param trans Position of the geometry in 3D space. Note that this is affected by the location of the parent node.
     * @param tex The path of the texture used for the material of the Sphere.
     * @param mode TextureMode - Original, Polar, or Projected (i.e. Sphere.TextureMode.Original)
     * @return Returns the resulting geometry.
     */
    public static Geometry createSphere(Node node, String name, float radius, Vector3f trans, String tex, Sphere.TextureMode mode){
        Sphere b = new Sphere(16, 16, radius);
        b.setTextureMode(mode);
        Geometry g = new Geometry(name, b);
        Material m = Util.getMaterial(Sys.getAssetManager(), tex);
        g.setMaterial(m);
        g.setLocalTranslation(trans);
        if(node != null) {
            node.attachChild(g);
        }
        return g;
    }
}
