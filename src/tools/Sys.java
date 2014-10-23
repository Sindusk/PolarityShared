package tools;

import action.ActionManager;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.input.InputManager;
import com.jme3.renderer.Camera;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import com.jme3.system.Timer;
import input.ClientInputHandler;
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
    
    // Nodes:
    private static Node rootNode;
    public static Node getRootNode(){
        return rootNode;
    }
    public static void setRootNode(Node rootNode){
        Sys.rootNode = rootNode;
    }
    
    private static Node collisionNode;
    public static Node getCollisionNode(){
        return collisionNode;
    }
    public static void setCollisionNode(Node collisionNode){
        Sys.collisionNode = collisionNode;
    }
    
    // Managers:
    private static AssetManager assetManager;
    public static AssetManager getAssetManager(){
        return assetManager;
    }
    public static void setAssetManager(AssetManager assetManager){
        Sys.assetManager = assetManager;
    }
    
    private static InputManager inputManager;
    public static InputManager getInputManager(){
        return inputManager;
    }
    public static void setInputManager(InputManager inputManager){
        Sys.inputManager = inputManager;
    }
    
    private static RenderManager renderManager;
    public static RenderManager getRenderManager(){
        return renderManager;
    }
    public static void setRenderManager(RenderManager renderManager){
        Sys.renderManager = renderManager;
    }
    
    private static AppStateManager stateManager;
    public static AppStateManager getStateManager(){
        return stateManager;
    }
    public static void setStateManager(AppStateManager stateManager){
        Sys.stateManager = stateManager;
    }
    
    // Other:
    private static ActionManager actionManager;
    public static ActionManager getActionManager(){
        return actionManager;
    }
    public static void setActionManager(ActionManager actionManager){
        Sys.actionManager = actionManager;
    }
    
    private static BulletAppState bulletAppState;
    public static BulletAppState getBulletAppState(){
        return bulletAppState;
    }
    public static void setBulletAppState(BulletAppState bulletAppState){
        Sys.bulletAppState = bulletAppState;
    }
    
    private static Camera camera;
    public static Camera getCamera(){
        return camera;
    }
    public static void setCamera(Camera camera){
        Sys.camera = camera;
    }
    
    private static ClientInputHandler inputHandler;
    public static ClientInputHandler getInputHandler(){
        return inputHandler;
    }
    public static void setInputHandler(ClientInputHandler inputHandler){
        Sys.inputHandler = inputHandler;
    }
    
    private static String version;
    public static String getVersion(){
        return version;
    }
    public static void setVersion(String version){
        Sys.version = version;
    }
    
    private static ViewPort viewPort;
    public static ViewPort getViewPort(){
        return viewPort;
    }
    public static void setViewPort(ViewPort viewPort){
        Sys.viewPort = viewPort;
    }
    
    private static Timer timer;
    public static Timer getTimer(){
        return timer;
    }
    public static void setTimer(Timer timer){
        Sys.timer = timer;
    }
    
    private static GameNetwork network;
    public static GameNetwork getNetwork(){
        return network;
    }
    public static void setNetwork(GameNetwork network){
        Sys.network = network;
    }
    
    private static World world;
    public static World getWorld(){
        return world;
    }
    public static void setWorld(World world){
        Sys.world = world;
    }
}
