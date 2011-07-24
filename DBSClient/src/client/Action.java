
package client;

/**
 *
 * @author xissburg
 */
public interface Action {
    
    void step(float dt);
    
    void update(float t);
    
    boolean isFinished();
}
