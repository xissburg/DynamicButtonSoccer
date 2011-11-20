
package actions;

/**
 * An actions that lasts for a defined finite amount of time.
 * 
 * @author xissburg
 */
public abstract class FiniteAction implements Action {

    private final float duration;
    private float time;
    private boolean finished;
    private boolean first;
    
    public FiniteAction(float duration) {
        this.duration = duration;
        this.time = 0;
        this.finished = false;
        this.first = true;
    }
    
    /**
     * To be overriden by subclasses. This method is called in every call to step
     * and it provides the implementer a parameter in the [0,1] interval. 0 is the
     * beginning and 1 is the end.
     * 
     * @param t Parameter in [0,1] interval.
     */
    public abstract void update(float t);
    
    @Override
    public void step(float dt) {
        if (first) {
            first = false;
            time = 0f;
            
            if (Float.compare(duration, 0f) == 0) { //special case for one-time actions
                update(0f);
                finished = true;
                return;
            }
            else {
                update(time/duration);
            }
        }
                
        if (dt > 0f)
        {
            time += dt;

            if (time < duration) {
                update(time/duration);
            }
            else if (!finished){
                update(1.f);
                finished = true;
            }
        }
    }

    @Override
    public boolean isFinished() {
        return finished;
    }
    
    public float getDuration() {
        return duration;
    }
}
