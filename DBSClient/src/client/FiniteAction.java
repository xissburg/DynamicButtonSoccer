/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

/**
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
    
    @Override
    public void step(float dt) {
        if (first) {
            first = false;
            time = 0;
        }
        else {
            time += dt;
        }
        
        if (time < duration) {
            update(time/duration);
        }
        else if (!finished){
            update(1.f);
            finished = true;
        }
    }

    @Override
    public boolean isFinished() {
        return finished;
    }
}
