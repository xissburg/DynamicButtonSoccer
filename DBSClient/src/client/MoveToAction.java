/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;

/**
 *
 * @author xissburg
 */
public class MoveToAction extends FiniteAction {

    private Spatial target;
    private Vector3f startPosition, endPosition, currentPosition;;
    
    public MoveToAction(float duration, Spatial target, Vector3f position) {
        super(duration);
        this.target = target;
        this.endPosition = position;
        this.startPosition = new Vector3f(target.getLocalTranslation());
        this.currentPosition = new Vector3f(this.startPosition);
    }
    
    @Override
    public void update(float t) {
        currentPosition.set(startPosition);
        currentPosition.interpolate(endPosition, t);
        target.setLocalTranslation(currentPosition);
    }
}
