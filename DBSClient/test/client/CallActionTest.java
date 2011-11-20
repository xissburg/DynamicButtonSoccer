/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import actions.ActionCallable;
import actions.CallAction;
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
public class CallActionTest {
    
    public CallActionTest() {
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
     * Test of step method, of class CallAction.
     */
    @Test
    public void testStep() {
        System.out.println("step");
        
        final StringBuilder sb = new StringBuilder();
        
        CallAction action = new CallAction(new ActionCallable() {
            public void call() {
                sb.append("xissburg");
            }
        });
        
        action.step((float)Math.random());
        
        assertEquals(sb.toString(), "xissburg"); //ensure call() was called
    }

    /**
     * Test of isFinished method, of class CallAction.
     */
    @Test
    public void testIsFinished() {
        System.out.println("isFinished");
        
        CallAction action = new CallAction(new ActionCallable() {
            public void call() {
                //no need to do anything
            }
        });
        
        action.step((float)Math.random());
        
        assertTrue(action.isFinished());
    }
}
