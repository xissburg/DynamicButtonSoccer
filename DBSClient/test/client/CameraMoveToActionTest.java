/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import actions.CameraMoveToAction;
import java.lang.reflect.Field;
import com.jme3.renderer.Camera;
import com.jme3.math.Vector3f;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author xissburg
 */
public class CameraMoveToActionTest {
    
    public CameraMoveToActionTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of update method, of class CameraMoveToAction.
     */
    @Test
    public void testUpdate() throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        System.out.println("CameraMoveToAction.update");
        
        float duration = 3f;
        int width = 1024, height = 768;
        Vector3f startLocation = new Vector3f(0f, 1f, 2f);
        Vector3f endLocation = new Vector3f(10f, 20f, 30f);
        
        Camera camera = new Camera(width, height);

        camera.setFrustumPerspective(45f, (float)width/height, 1f, 1000f);
        camera.setLocation(startLocation);
        camera.lookAt(new Vector3f(0f, 0f, 0f), Vector3f.UNIT_Y);
        
        camera.setLocation(startLocation);
        CameraMoveToAction action = new CameraMoveToAction(duration, camera, endLocation);
        
        Field startLocationField = action.getClass().getDeclaredField("startLocation");
        startLocationField.setAccessible(true);
        
        Field currentLocationField = action.getClass().getDeclaredField("currentLocation");
        currentLocationField.setAccessible(true);
        
        Field endLocationField = action.getClass().getDeclaredField("endLocation");
        endLocationField.setAccessible(true);
        
        //0
        action.step(duration*0f);
        
        assertEquals(startLocationField.get(action), startLocation);
        assertEquals(currentLocationField.get(action), startLocation);
        assertEquals(endLocationField.get(action), endLocation);
        
        //0.5
        action.step(duration*0.5f);
        
        Vector3f currentLocation = new Vector3f(startLocation);
        currentLocation.interpolate(endLocation, 0.5f);
        
        assertEquals(startLocationField.get(action), startLocation);
        assertEquals(currentLocationField.get(action), currentLocation);
        assertEquals(endLocationField.get(action), endLocation);
        
        //1.0
        action.step(duration*1f);
        
        assertEquals(startLocationField.get(action), startLocation);
        assertEquals(currentLocationField.get(action), endLocation);
        assertEquals(endLocationField.get(action), endLocation);
    }
}
