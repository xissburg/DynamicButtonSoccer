
package actions;

/**
 * Calls the call() method of an ActionCallable
 * 
 * @author xissburg
 */
public class CallAction extends FiniteAction {

    private ActionCallable actionCallable;
    
    public CallAction(ActionCallable actionCallable) {
        super(0f);
        this.actionCallable = actionCallable;
    }

    @Override
    public void update(float dt) {
        actionCallable.call();
    }
}
