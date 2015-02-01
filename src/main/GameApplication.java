package main;

import character.CharacterManager;
import com.jme3.app.Application;
import com.jme3.scene.Node;
import netdata.ChunkData;
import world.World;

/**
 *
 * @author SinisteRing
 */
public class GameApplication extends Application {
    protected static final String VERSION = "0.03";
    protected CharacterManager charManager;
    protected World world = new World(50);
    protected Node root = new Node("Root");
    protected Node gui = new Node("GUI");
    
    @Override
    public void start(){
        charManager = new CharacterManager();
        super.start();
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
