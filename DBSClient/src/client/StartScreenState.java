
package client;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.scene.Node;
import de.lessvoid.nifty.Nifty;

/**
 * AppState for the title screen with a simple gui.
 * @author xissburg
 */
public class StartScreenState extends AbstractAppState {

    private Node rootNode;
    private ClientApplication app;

    public StartScreenState(Node rootNode) {
        this.rootNode = rootNode;
        this.initialized = false;
    }
        
    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        //app *must* be an instance of ClientApplication so that methods like runAction
        //can be called on it
        this.app = (ClientApplication)app;
        
        //Add gui to the screen
        NiftyJmeDisplay niftyDisplay = new NiftyJmeDisplay(this.app.getAssetManager(), 
                this.app.getInputManager(), this.app.getAudioRenderer(), this.app.getGuiViewPort());
        Nifty nifty = niftyDisplay.getNifty();
        nifty.fromXml("Interfaces/TitleScreen.xml", "start");
        this.app.getGuiViewPort().addProcessor(niftyDisplay);
        
        //Setup event handlers
        
    }
    
    
}
