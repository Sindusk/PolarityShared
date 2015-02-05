package tools;

import com.jme3.asset.AssetManager;
import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.font.BitmapFont;
import com.jme3.input.InputManager;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseAxisTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.material.Material;
import com.jme3.material.RenderState.BlendMode;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Ray;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.network.HostedConnection;
import com.jme3.network.serializing.Serializable;
import com.jme3.renderer.Camera;
import com.jme3.scene.Node;
import com.jme3.texture.Texture;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import netdata.DevLogData;

/**
 * T (Tools) - Provides miscellaneous tools for various functions.
 * @author SinisteRing
 */
public class Util {
    public static final float ROOT_HALF = 1.0f/FastMath.sqrt(2);
    
    @Serializable
    public static class Vector2i{
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
    
    // Commands
    public static void handleCommand(String command){
        //TBI
    }
    
    // Parsing Assist:
    public static ArrayList<String> getArgs(String s){
        if(s.contains("(") && s.contains(")")){
            return new ArrayList<String>(Arrays.asList(s.substring(s.indexOf("(")+1, s.indexOf(")")).split(",")));
        }
        return new ArrayList<String>(Arrays.asList("".split(",")));
    }
    public static ArrayList<String> getInnerArgs(String s){
        if(s.contains("[") && s.contains("]")){
            return new ArrayList<String>(Arrays.asList(s.substring(s.indexOf("[")+1, s.indexOf("]")).split(";")));
        }
        return new ArrayList<String>(Arrays.asList("".split(";")));
    }
    public static String getHeader(String s){
        if(s.contains("(")){
            return s.substring(0, s.indexOf("("));
        }
        return s;
    }
    public static String getInnerHeader(String s){
        if(s.contains("[")){
            return s.substring(0, s.indexOf("["));
        }
        return s;
    }
    
    // Asset Management:
    public static BitmapFont getFont(AssetManager assetManager, String fnt){
        return assetManager.loadFont("Interface/Fonts/"+fnt+".fnt");
    }
    public static Material getMaterial(AssetManager assetManager, ColorRGBA color){
        Material m = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        m.setColor("Color", color);
        if(color.getAlpha() < 1){
            m.getAdditionalRenderState().setBlendMode(BlendMode.Alpha);
        }
        return m;
    }
    public static Material getMaterial(AssetManager assetManager, String tex){
        Material m = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        m.setTexture("ColorMap", assetManager.loadTexture(tex));
        m.getTextureParam("ColorMap").getTextureValue().setWrap(Texture.WrapMode.Repeat);
        m.getTextureParam("ColorMap").getTextureValue().setMagFilter(Texture.MagFilter.Nearest);
        return m;
    }
    public static String getBlockIcon(String tex){
        return "Interface/Icons/Blocks/"+tex+".png";
    }
    public static String getItemIcon(String tex){
        return "Interface/Icons/Items/"+tex+".png";
    }
    
    // Key Mappings:
    public static void createMapping(InputManager inputManager, ActionListener listener, String name, KeyTrigger trigger){
        inputManager.addMapping(name, trigger);
        inputManager.addListener(listener, name);
    }
    public static void createMapping(InputManager inputManager, ActionListener listener, String name, MouseButtonTrigger trigger){
        inputManager.addMapping(name, trigger);
        inputManager.addListener(listener, name);
    }
    public static void createMapping(InputManager inputManager, AnalogListener listener, String name, MouseAxisTrigger trigger){
        inputManager.addMapping(name, trigger);
        inputManager.addListener(listener, name);
    }
    
    // Numbers:
    public static float roundedFloat(float value, int spaces){
        return Math.round(value*Math.pow(10f, spaces))/(float)Math.pow(10f, spaces);
    }
    
    // Random numbers:
    public static float randFloat(float min, float max){
        return (FastMath.nextRandomFloat()*(max-min))+min;
    }
    public static float roundedRandFloat(float min, float max, int spaces){
        return Math.round(randFloat(min, max)*Math.pow(10f, spaces))/(float)Math.pow(10f, spaces);
    }
    
    // This variable scales the result of the random float generation.
    // Higher values will deflate the result, causing high values to be more rare
    // Lower values will inflate the result, causing high values to be more common
    protected static final float SCALE_EXPONENT = 1f;
    // Random number scaling algorithm: result = 1-sqrt(1-rng^[variable])
    // rng: The value produced by FastMath.nextRandomFloat() (a 0-1 float with an equal spread)
    // variable: SCALE_EXPONENT
    public static float scaledRandFloat(float min, float max){
        return ((1-(float)Math.sqrt(1-(float)Math.pow(FastMath.nextRandomFloat(), SCALE_EXPONENT)))*(max-min))+min;
    }
    // This function simply rounds the resulting float from scaledRandFloat() above.
    // Done by multiplying the result by 10^spaces, rounding to nearest int value, then dividing by 10^spaces 
    public static float roundedScaledRandFloat(float min, float max, int spaces){
        return Math.round(scaledRandFloat(min, max)*Math.pow(10f, spaces))/(float)Math.pow(10f, spaces);
    }
    
    // Mouse targeting and collision:
    public static CollisionResult getClosestCollision(Node node, Ray ray){
        CollisionResults results = new CollisionResults();
        node.collideWith(ray, results);
        String name;
        int i = 0;
        while(i < results.size()){
            name = results.getCollision(i).getGeometry().getName();
            if(!name.equals("BitmapFont")){
                return results.getCollision(i);
            }
            i++;
        }
        return null;
    }
    public static Vector3f getWorldDir(Vector3f loc, Vector2f mouseLoc, Camera cam){
        return cam.getWorldCoordinates(mouseLoc, 1f).subtract(loc).normalize();
    }
    public static Vector3f getWorldLoc(Vector2f mouseLoc, Camera cam){
        return cam.getWorldCoordinates(mouseLoc, 0f).clone();
    }
    public static CollisionResult getMouseTarget(Vector2f mouseLoc, Camera cam, Node node){
        Vector3f loc = getWorldLoc(mouseLoc, cam);
        Vector3f dir = getWorldDir(loc, mouseLoc, cam);
        return getClosestCollision(node, new Ray(loc, dir));
    }
    
    // Vectors and gamespace:
    public static Vector3f getOffset(Node node){
        Node recursiveNode = node;
        Vector3f offset = new Vector3f(0, 0, 0);
        Vector3f helper;
        do{
            helper = recursiveNode.getLocalTranslation();
            offset = new Vector3f(offset.x+helper.x, offset.y+helper.y, offset.z+helper.z);
            recursiveNode = recursiveNode.getParent();
        }while(recursiveNode != null);
        return offset;
    }
    
    // Logging
    public static void log(HostedConnection conn, String s){
        conn.send(new DevLogData(s));
    }
    public static void log(String s, int debug){
        if(Sys.debug >= debug){
            System.out.println(s);
        }
    }
    public static void log(String s){
        System.out.println(s);
    }
    public static void log(float f){
        System.out.println(f);
    }
    public static void log(Throwable t){
        Logger.getLogger("polarity").log(Level.SEVERE, "{0}", t);
    }
}
