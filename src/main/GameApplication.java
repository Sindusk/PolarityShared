package main;

import com.jme3.app.Application;
import com.jme3.scene.Node;
import netdata.ChunkData;
import world.World;

/**
 *
 * @author SinisteRing
 */
public class GameApplication extends Application {
    protected static final String VERSION = "0.02";
    protected World world = new World(50);
    protected Node root = new Node("Root");
    protected Node gui = new Node("GUI");
    
    public String getVersion(){
        return VERSION;
    }
    public World getWorld(){
        return world;
    }
    public void updateChunk(ChunkData d){
        world.updateChunk(d);
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
