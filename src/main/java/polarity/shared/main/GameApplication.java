package polarity.shared.main;

import com.jme3.app.LegacyApplication;
import com.jme3.math.ColorRGBA;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import polarity.shared.tools.GeoFactory;
import polarity.shared.tools.Sys;
import polarity.shared.tools.Util;
import polarity.shared.world.GameWorld;

/**
 *
 * @author Sindusk
 */
public class GameApplication extends LegacyApplication {
    protected static final String VERSION = "0.04";
    protected GameWorld world;
    protected Node root = new Node("Root");
    protected Node gui = new Node("GUI");
    
    @Override
    public void start(){
        Util.log("GameApplication start()");
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
        
        //world = new GameWorld(50);
        //Sys.setWorld(world);
    }
    
    public String getVersion(){
        return VERSION;
    }
    public GameWorld getWorld(){
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
