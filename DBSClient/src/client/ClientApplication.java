
package client;

import com.jme3.app.Application;
import com.jme3.renderer.queue.RenderQueue.Bucket;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial.CullHint;
import com.jme3.system.AppSettings;

/**
 *
 * @author xissburg
 */
public class ClientApplication extends Application {
    
    private Node rootNode;
    private Node guiNode;
    private ActionsState actionsState;
    
    public ClientApplication() {
        super();
        rootNode = new Node("root");
        guiNode = new Node("gui");
        actionsState = new ActionsState();
    }
    
    @Override
    public void initialize() {
        super.initialize();
        
        guiNode.setQueueBucket(Bucket.Gui);
        guiNode.setCullHint(CullHint.Never);
        
        viewPort.attachScene(rootNode);
        guiViewPort.attachScene(guiNode);
        
        stateManager.attach(actionsState);
        
        //Create and attach the initial screen state
        StartScreenState startScreenState = new StartScreenState(rootNode);
        stateManager.attach(startScreenState);
    }
    
    public void runAction(Action action) {
        actionsState.runAction(action);
    }
    
    public void start() {
        // set some default settings in-case
        // settings dialog is not shown
        boolean loadSettings = false;
        if (settings == null) {
            setSettings(new AppSettings(true));
            loadSettings = true;
        }
        
        settings.put("Title", "Dynamic Button Soccer");

        //re-setting settings they can have been merged from the registry.
        setSettings(settings);
        
        super.start();
    }
    
    @Override
    public void update() {
        super.update(); // makes sure to execute AppTasks
        if (speed == 0 || paused) {
            return;
        }

        float tpf = timer.getTimePerFrame() * speed;

        // update states
        stateManager.update(tpf);

        rootNode.updateLogicalState(tpf);
        guiNode.updateLogicalState(tpf);
        
        rootNode.updateGeometricState();
        guiNode.updateGeometricState();

        // render states
        stateManager.render(renderManager);
        
        if (context.isRenderable()){
            renderManager.render(tpf);
        }
        
        stateManager.postRender();
    }
}
