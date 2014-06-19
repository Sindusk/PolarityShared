package main;

import com.jme3.app.Application;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.system.AppSettings;
import input.ClientInputHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import network.ClientNetwork;
import screens.MenuScreen;
import screens.Screen;
import tools.Sys;
import tools.Util;

/**
Copyright (c) 2003-2012 jMonkeyEngine
All rights reserved.
 
Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are
met:
 
Redistributions of source code must retain the above copyright
notice, this list of conditions and the following disclaimer.
 
Redistributions in binary form must reproduce the above copyright
notice, this list of conditions and the following disclaimer in the
documentation and/or other materials provided with the distribution.
 
Neither the name of 'jMonkeyEngine' nor the names of its contributors 
may be used to endorse or promote products derived from this software 
without specific prior written permission.
 
THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
public class GameClient extends Application {
    private static final String CLIENT_VERSION = "0.01";
    protected Node rootNode = new Node("Root Node");
    protected Node guiNode = new Node("Gui Node");
    protected ClientInputHandler inputHandler;
    protected ClientNetwork clientNetwork;
    
    public static void main(String[] args){
        GameClient app = new GameClient();
        app.start();
    }
    
    public ClientNetwork getNetwork(){
        return clientNetwork;
    }
    
    @Override
    public void start() {
        Logger.getLogger("com.jme3").setLevel(Level.WARNING);
        settings = new AppSettings(true);
        settings.setSamples(0);
        settings.setVSync(false);
        settings.setRenderer(AppSettings.LWJGL_OPENGL1);
        settings.setResolution(1200, 750);
        settings.setTitle("Reach");
        this.setSettings(settings);
        super.start();
    }

    @Override
    public void initialize() {
        super.initialize();
        Util.log("[GameClient] <initialize> Starting Initialization...", 1);
        guiNode.setQueueBucket(RenderQueue.Bucket.Gui);
        guiNode.setCullHint(Spatial.CullHint.Never);
        viewPort.attachScene(rootNode);
        guiViewPort.attachScene(guiNode);
        
        setPauseOnLostFocus(false);
        
        // Initialize system variables
        Sys.height = settings.getHeight();
        Sys.width = settings.getWidth();
        Sys.setAssetManager(assetManager);
        Sys.setCamera(cam);
        Sys.setInputManager(inputManager);
        Sys.setRenderManager(renderManager);
        Sys.setStateManager(stateManager);
        Sys.setTimer(timer);
        Sys.setVersion(CLIENT_VERSION);
        Sys.setViewPort(viewPort);
        
        // Initialize camera
        cam.setParallelProjection(true);
        float width=25f*Sys.width/1000f;
        float height=25f*Sys.height/800f;
        cam.setFrustum(1.0f, 100f, -width, width, height, -height);
        cam.update();
        
        // Initialize input handler
        inputHandler = new ClientInputHandler(inputManager);
        inputHandler.setupInputs();
        
        // Initialize networking
        clientNetwork = new ClientNetwork(this, rootNode, guiNode);
        clientNetwork.setInputHandler(inputHandler);
        
        // Initialize Screen static vars
        Screen.setApplication(this);
        Screen.setClientNetwork(clientNetwork);
        Screen.setNodes(rootNode, guiNode);
        
        inputHandler.switchScreens(new MenuScreen(this, rootNode, guiNode));
        Sys.setInputHandler(inputHandler);
    }

    @Override
    public void update() {
        super.update();
        if (speed == 0 || paused){
            return;
        }
        float tpf = timer.getTimePerFrame() * speed;    // Calculated time from last frame for keeping time consistency through FPS fluctuations.
        
        // Custom updates
        inputHandler.update(tpf);
        
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
    
    @Override
    public void destroy(){
        if(clientNetwork != null && clientNetwork.isConnected()){
            clientNetwork.disconnect();
        }
        super.destroy();
    }
}
