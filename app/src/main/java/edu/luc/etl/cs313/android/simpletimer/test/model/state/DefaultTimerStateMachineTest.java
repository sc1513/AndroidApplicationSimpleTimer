package edu.luc.etl.cs313.android.simpletimer.test.model.state;

import org.junit.After;
import org.junit.Before;

import edu.luc.etl.cs313.android.simpletimer.model.state.DefaultTimerStateMachine;

/**
 * Concrete testcase subclass for the default timer state machine
 * implementation.
 *
 * @author 413/313 group1
 */
public class DefaultTimerStateMachineTest extends AbstractTimerStateMachineTest {

    @Before
    public void setUp() throws Exception {
        super.setUp();
        setModel(new DefaultTimerStateMachine(getDependency(), getDependency(), getDependency()));
    }

    @After
    public void tearDown() {
        setModel(null);
        super.tearDown();
    }
}
