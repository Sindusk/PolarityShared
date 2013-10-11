package mygame;

import com.jme3.app.Application;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.system.AppSettings;
import java.util.logging.Level;
import java.util.logging.Logger;

/**

 */
public class Main extends Application {

    public Node rootNode = new Node("Root Node");
    protected Node guiNode = new Node("Gui Node");
    
    public static void main(String[] args) {
        Main app = new Main();
        app.start();
    }
    
    @Override
    public void start() {
        Logger.getLogger("com.jme3").setLevel(Level.WARNING);
        settings = new AppSettings(true);
        settings.setRenderer(AppSettings.LWJGL_OPENGL1);
        settings.setResolution(800, 600);
        settings.setTitle("Tactics");
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
        
        
        
    }

    @Override
    public void update() {
        super.update(); 
        if (speed == 0 || paused) {
            return;
        }
        float tpf = timer.getTimePerFrame() * speed;
        
        
        
        
        //yes, these are necessary, Jmonkey just thinks I'm using SIMPLEAPPLICATION. Incorrect.
        rootNode.updateLogicalState(tpf);
        guiNode.updateLogicalState(tpf);
        rootNode.updateGeometricState();
        guiNode.updateGeometricState();

        // render states
        stateManager.render(renderManager);
        renderManager.render(tpf, context.isRenderable());
        stateManager.postRender();
    }
}
