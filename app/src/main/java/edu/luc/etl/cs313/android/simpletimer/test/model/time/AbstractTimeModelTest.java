package edu.luc.etl.cs313.android.simpletimer.test.model.time;

import static edu.luc.etl.cs313.android.simpletimer.common.Constants.SEC_PER_TICK;
import static edu.luc.etl.cs313.android.simpletimer.common.Constants.SEC_MAX;
import static edu.luc.etl.cs313.android.simpletimer.common.Constants.SEC_MIN;
import static edu.luc.etl.cs313.android.simpletimer.common.Constants.TIMEOUT_VALUE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import edu.luc.etl.cs313.android.simpletimer.model.time.TimeModel;

/**
 * Testcase superclass for the time model abstraction.
 * This is a simple unit test of an object without dependencies.
 *
 * @author 413/313 group1
 */
public abstract class AbstractTimeModelTest {

    private TimeModel model;

    /**
     * Setter for dependency injection. Usually invoked by concrete testcase
     * subclass.
     *
     * @param model
     */
    protected void setModel(final TimeModel model) {
        this.model = model;
    }

    /**
     * Verifies that runtime and laptime are initially 0 or less.
     */
    @Test
    public void testPreconditions() {
        assertEquals(0, model.getRuntime());
    }

    /**
     * Verifies that runtime is incremented correctly.
     */
    @Test
    public void testDecrementRuntimeOne() {
        model.setRuntime( 5 );
        final int rt = model.getRuntime();
        model.decRuntime();
        assertEquals(rt - SEC_PER_TICK, model.getRuntime());
    }

    /**
     * Verifies that runtime turns over correctly.
     */
    @Test
    public void testDecrementRuntimeMany() {
        model.setRuntime( SEC_MAX );
        final int rt = model.getRuntime();
        for (int i = 0; i < 101; i ++) {
            model.decRuntime();
        }
        assertEquals(0, model.getRuntime());
    }

    /**
     * Verifies that runtime is incremented correctly.
     */
    @Test
    public void testIncrementRuntimeOne() {
        final int rt = model.getRuntime();
        model.incRuntime();
        assertEquals(rt + SEC_PER_TICK, model.getRuntime());
    }

    /**
     * Verifies that runtime turns over correctly.
     */
    @Test
    public void testIncrementRuntimeMany() {
        final int rt = model.getRuntime();
        for (int i = 0; i < 100; i ++) {
            model.incRuntime();
        }
        assertEquals(SEC_MAX, model.getRuntime());
    }

    /**
     * Verifies that timeout is decremented correctly.
     */
    @Test
    public void testDecrementTimeoutOne() {
        final int timeout = TIMEOUT_VALUE;
        model.resetTimeout();
        final int timeoutreturn = model.decTimeout();
        assertEquals(timeout - SEC_PER_TICK, timeoutreturn );
    }

    /**
     * Verifies that timeout turns over correctly after decremented many times.
     */
    @Test
    public void testDecrementTimeoutMany() {
        int timeout = TIMEOUT_VALUE;
        model.resetTimeout();
        for (int i = 0; i < TIMEOUT_VALUE + 1; i ++) {
            timeout = model.decTimeout();
        }
        assertEquals(timeout, 0);
    }
}
