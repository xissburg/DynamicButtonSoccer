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
    private Vector3f startPosition, endPosition;
    
    public MoveToAction(float duration, Spatial target, Vector3f position) {
        super(duration);
        this.target = target;
        this.endPosition = position;
        this.startPosition = target.getLocalTranslation();
    }
    
    @Override
    public void update(float t) {
        Vector3f pos = new Vector3f(startPosition);
        pos.interpolate(endPosition, t);
        target.setLocalTranslation(pos);
    }
}
