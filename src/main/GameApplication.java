package main;

import character.CharacterManager;
import com.jme3.app.Application;
import com.jme3.math.ColorRGBA;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import tools.GeoFactory;
import tools.Sys;
import tools.Util;
import world.World;

/**
 *
 * @author SinisteRing
 */
public class GameApplication extends Application {
    protected static final String VERSION = "0.04";
    protected CharacterManager charManager;
    protected World world;
    protected Node root = new Node("Root");
    protected Node gui = new Node("GUI");
    
    @Override
    public void start(){
        //world = new World(50);
        //charManager = new CharacterManager();
        super.start();
    }
    
    @Override
    public void initialize(){
        super.initialize();
        
        // Initialize & create viewport
        Util.log("[GameApplication] <initialize> Creating Viewport...", 1);
        gui.setQueueBucket(RenderQueue.Bucket.Gui);
        gui.setCullHint(Spatial.CullHint.Never);
        viewPort.attachScene(root);
        guiViewPort.attachScene(gui);
        viewPort.setBackgroundColor(ColorRGBA.Black);
        setPauseOnLostFocus(false);
        
        // Initialize system variables
        Sys.height = settings.getHeight();
        Sys.width = settings.getWidth();
        Sys.setCamera(cam);
        Sys.setVersion(VERSION);
        
        GeoFactory.initialize(assetManager);
        Util.initialize(assetManager);
        
        world = new World(50);
        Sys.setWorld(world);
        charManager = new CharacterManager();
    }
    
    public String getVersion(){
        return VERSION;
    }
    public CharacterManager getCharManager(){
        return charManager;
    }
    public World getWorld(){
        return world;
    }
    
    public void updateNodeStates(float tpf){
        root.updateLogicalState(tpf);
        gui.updateLogicalState(tpf);
        root.updateGeometricState();
        gui.updateGeometricState();
    }
    public void renderDisplay(float tpf){
        stateManager.render(renderManager);
        renderManager.render(tpf, context.isRenderable());
        stateManager.postRender();
    }
}
