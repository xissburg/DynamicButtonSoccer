
package actions;

import com.jme3.app.Application;
import com.jme3.app.state.AppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.renderer.RenderManager;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 *
 * @author xissburg
 */
public class ActionsState implements AppState {
    
    private Collection<Action> actions;
    private boolean active;
    
    public ActionsState() {
        actions = new ArrayList<Action>();
        active = true;
    }
    
    public void runAction(Action action) {
        actions.add(action);
    }
    
    public void stopAction(Action action) {
        actions.remove(action);
    }
    
    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        
    }

    @Override
    public boolean isInitialized() {
        return true;
    }

    @Override
    public void setEnabled(boolean active) {
        this.active = active;
    }

    @Override
    public boolean isEnabled() {
        return active;
    }

    @Override
    public void stateAttached(AppStateManager stateManager) {
        
    }

    @Override
    public void stateDetached(AppStateManager stateManager) {
        
    }

    @Override
    public void update(float tpf) {
        if (!active) {
            return;
        }
        
        Iterator<Action> it = actions.iterator();
        
        while (it.hasNext()) {
            Action action = it.next();
            action.step(tpf);
            
            if (action.isFinished()) {
                it.remove();
            }
        }
    }

    @Override
    public void render(RenderManager rm) {}

    @Override
    public void postRender() {}

    @Override
    public void cleanup() {
        actions.clear();
    }
}
