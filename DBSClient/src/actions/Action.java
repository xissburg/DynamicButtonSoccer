
package actions;

/**
 *
 * @author xissburg
 */
public interface Action {
    
    void step(float dt);
    
    boolean isFinished();
}
