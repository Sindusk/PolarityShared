package main;

import com.jme3.app.Application;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.system.AppSettings;
import input.ClientInputHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import screens.MenuScreen;
import tools.S;

/**

 */
public class Main extends Application {
    private static final String CLIENT_VERSION = "0.01";
    protected Node rootNode = new Node("Root Node");
    protected Node guiNode = new Node("Gui Node");
    protected ClientInputHandler inputHandler;
    
    public static void main(String[] args) {
        Main app = new Main();
        app.start();
    }
    
    @Override
    public void start() {
        Logger.getLogger("com.jme3").setLevel(Level.WARNING);
        settings = new AppSettings(true);
        settings.setRenderer(AppSettings.LWJGL_OPENGL1);
        settings.setResolution(1000, 750);
        settings.setTitle("Reach");
        this.setSettings(settings);
        super.start();
    }

    @Override
    public void initialize() {
        super.initialize();
        guiNode.setQueueBucket(RenderQueue.Bucket.Gui);
        guiNode.setCullHint(Spatial.CullHint.Never);
        viewPort.attachScene(rootNode);
        guiViewPort.attachScene(guiNode);
        
        // Initialize system variables
        S.height = settings.getHeight();
        S.width = settings.getWidth();
        S.setAssetManager(assetManager);
        S.setCamera(cam);
        S.setInputManager(inputManager);
        S.setRenderManager(renderManager);
        S.setStateManager(stateManager);
        S.setTimer(timer);
        S.setVersion(CLIENT_VERSION);
        S.setViewPort(viewPort);
        
        // Initialize input handler
        inputHandler = new ClientInputHandler(inputManager, guiNode);
        inputHandler.setupInputs();
        inputHandler.switchScreens(new MenuScreen(rootNode, guiNode));
        S.setInputHandler(inputHandler);
    }

    @Override
    public void update() {
        super.update(); 
        if (speed == 0 || paused) {
            return;
        }
        float tpf = timer.getTimePerFrame() * speed;    // Calculated time from last frame for keeping time consistency through FPS fluctuations.
        
        // Update node states
        rootNode.updateLogicalState(tpf);
        guiNode.updateLogicalState(tpf);
        rootNode.updateGeometricState();
        guiNode.updateGeometricState();
        
        // Update renderer
        stateManager.render(renderManager);
        renderManager.render(tpf, context.isRenderable());
        stateManager.postRender();
    }
}
