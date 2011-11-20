
package client;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.scene.Node;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.TextField;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;

/**
 * AppState for the title screen with a simple gui.
 * @author xissburg
 */
public class StartScreenState extends AbstractAppState implements ScreenController {

    private Node rootNode;
    private ClientApplication app;
    Nifty nifty;

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
        Nifty n = niftyDisplay.getNifty();
        n.fromXml("Interfaces/TitleScreen.xml", "start", this);
        this.app.getGuiViewPort().addProcessor(niftyDisplay);
        
        //Setup event handlers
        
    }

    /**
     * ScreenController
     */
    @Override
    public void bind(Nifty nifty, Screen screen) {
        this.nifty = nifty;
    }

    @Override
    public void onStartScreen() {
        
    }

    @Override
    public void onEndScreen() {
        
    }
    
    /**
     * Screen Events
     */
    public void login() {
        String username = nifty.getCurrentScreen().
                findNiftyControl("UserNameTextField", TextField.class).getText();
        String password = nifty.getCurrentScreen().
                findNiftyControl("PasswordTextField", TextField.class).getText();
        String url = nifty.getCurrentScreen().
                findNiftyControl("URLTextField", TextField.class).getText();
        String port = nifty.getCurrentScreen().
                findNiftyControl("PortTextField", TextField.class).getText();
        System.out.println(String.format("Username: %s\nPassword: %s\nURL: %s\nPort: %s\n", 
                username, password, url, port));
    }
    
    public void quit() {
        app.stop();
    }
}
