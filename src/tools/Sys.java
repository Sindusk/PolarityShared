package tools;

import com.jme3.renderer.Camera;
import network.GameNetwork;
import world.World;

/**
 *
 * @author SinisteRing
 */
public class Sys {
    public static final int GROUP_TERRAIN = 0;
    public static final int GROUP_PLAYER = 1;
    public static final int GROUP_ENTITY = 2;
    
    public static int height = 0;
    public static int width = 0;
    
    public static int debug = 0;
    
    // Camera:
    private static Camera camera;
    public static Camera getCamera(){
        return camera;
    }
    public static void setCamera(Camera camera){
        Sys.camera = camera;
    }
    
    // Application Version:
    private static String version;
    public static String getVersion(){
        return version;
    }
    public static void setVersion(String version){
        Sys.version = version;
    }
    
    // Network, can be either a ServerNetwork or ClientNetwork
    private static GameNetwork network;
    public static GameNetwork getNetwork(){
        return network;
    }
    public static void setNetwork(GameNetwork network){
        Sys.network = network;
    }
    
    // World:
    private static World world;
    public static World getWorld(){
        return world;
    }
    public static void setWorld(World world){
        Sys.world = world;
    }
}
