/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import actions.MoveToAction;
import java.lang.reflect.Field;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
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
public class MoveToActionTest {
    
    public MoveToActionTest() {
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
     * Test of update method, of class MoveToAction.
     */
    @Test
    public void testUpdate() throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        System.out.println("MoveToAction.update");
        
        float duration = 3f;
        Vector3f startLocation = new Vector3f(0f, 1f, 2f);
        Vector3f endLocation = new Vector3f(10f, 20f, 30f);
        
        Geometry geom = new Geometry("geom");
        geom.setLocalTranslation(startLocation);
        MoveToAction action = new MoveToAction(duration, geom, endLocation);
        
        Field startPositionField = action.getClass().getDeclaredField("startPosition");
        startPositionField.setAccessible(true);
        
        Field currentPositionField = action.getClass().getDeclaredField("currentPosition");
        currentPositionField.setAccessible(true);
        
        Field endPositionField = action.getClass().getDeclaredField("endPosition");
        endPositionField.setAccessible(true);
        
        //0
        action.step(duration*0f);
        
        assertEquals(startPositionField.get(action), startLocation);
        assertEquals(currentPositionField.get(action), startLocation);
        assertEquals(endPositionField.get(action), endLocation);
        
        //0.5
        action.step(duration*0.5f);
        
        Vector3f currentLocation = new Vector3f(startLocation);
        currentLocation.interpolate(endLocation, 0.5f);
        
        assertEquals(startPositionField.get(action), startLocation);
        assertEquals(currentPositionField.get(action), currentLocation);
        assertEquals(endPositionField.get(action), endLocation);
        
        //1.0
        action.step(duration*1f);
        
        assertEquals(startPositionField.get(action), startLocation);
        assertEquals(currentPositionField.get(action), endLocation);
        assertEquals(endPositionField.get(action), endLocation);
    }
}
