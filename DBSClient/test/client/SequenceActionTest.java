
package client;

import actions.ActionCallable;
import actions.SequenceAction;
import actions.DelayAction;
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
public class SequenceActionTest {
    
    public SequenceActionTest() {
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
        System.out.println("SequenceAction.step()");
        
        float duration = 1f;
        
        DelayAction a0 = new DelayAction(duration);
        
        final StringBuilder sb = new StringBuilder();
        
        CallAction a1 = new CallAction(new ActionCallable() {
            public void call() {
                sb.append("xissburg");
            }
        });
        
        SequenceAction action = new SequenceAction(a0, a1);
        
        action.step(duration/2);
        
        assertEquals(sb.toString(), "");
        
        action.step(duration/2);
        
        assertEquals(sb.toString(), "xissburg"); //call() should have been called now
    }
}
