/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package actions;

import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;

/**
 *
 * @author xissburg
 */
public class CameraMoveToAction extends FiniteAction {

    private Camera camera;
    private Vector3f endLocation, startLocation, currentLocation;
    
    public CameraMoveToAction(float duration, Camera camera, Vector3f location) {
        super(duration);
        this.camera = camera;
        this.endLocation = location;
        this.startLocation = new Vector3f(camera.getLocation());
        this.currentLocation = new Vector3f(this.startLocation);
    }
    
    @Override
    public void update(float t) {
        currentLocation.set(startLocation);
        currentLocation.interpolate(endLocation, t);
        camera.setLocation(currentLocation);
    }
}
