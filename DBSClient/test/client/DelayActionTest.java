/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import actions.DelayAction;
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
public class DelayActionTest {
    
    public DelayActionTest() {
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
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    
    @Test
    public void testStep() {
        System.out.println("DelayAction.step()");
        
        float duration = 4f;
        DelayAction action = new DelayAction(duration);
        
        action.step(duration/0.9f);
        
        assertFalse(action.isFinished());
        
        action.step(duration/0.1f);
        
        assertTrue(action.isFinished());
    }
}
