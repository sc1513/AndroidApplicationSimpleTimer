package edu.luc.etl.cs313.android.simpletimer.test.model.alarm;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;

import edu.luc.etl.cs313.android.simpletimer.model.alarm.AlarmModel;

import static org.junit.Assert.assertEquals;


/**
 * Test case superclass for the autonomous alarm model abstraction.
 * Unit-tests the pseudo-real-time behavior of the alarm.
 * Uses a simple stub object to satisfy the alarm's dependency.
 *
 * @author 413/313 group1
 */
public abstract class AbstractAlarmModelTest {

    private AlarmModel model;

    /**
     * Setter for dependency injection. Usually invoked by concrete testcase
     * subclass.
     *
     * @param model
     */
    protected void setModel(final AlarmModel model) {
        this.model = model;
    }

    protected AlarmModel getModel() {
        return model;
    }

    /**
     * Verifies check play status is stopped.
     *
     *
     */
    @Test
    public void testStopAlarm() {

    }

    /**
     * check play status is playing.
     *
     *
     */
    @Test
    public void testPlayAlarm()  {

    }
}
