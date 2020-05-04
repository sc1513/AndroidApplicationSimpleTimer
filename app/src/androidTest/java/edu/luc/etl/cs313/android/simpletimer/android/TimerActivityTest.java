package edu.luc.etl.cs313.android.simpletimer.android;

import android.test.ActivityInstrumentationTestCase2;

import edu.luc.etl.cs313.android.simpletimer.test.android.AbstractTimerActivityTest;

/**
 * Concrete Android test subclass. Has to inherit from framework class
 * and uses delegation to concrete subclass of abstract test superclass.
 * IMPORTANT: project must export JUnit 4 to make it available on the
 * device.
 *
 * @author 413/313 group1
 * @see "https://developer.android.com/training/testing/ui-testing/"
 */
public class TimerActivityTest extends ActivityInstrumentationTestCase2<TimerAdapter> {

    /**
     * Creates an {@link ActivityInstrumentationTestCase2} for the
     * {@link TimerAdapter} activity.
     */
    public TimerActivityTest() {
        super(TimerAdapter.class);
        actualTest = new AbstractTimerActivityTest() {
            @Override
            protected TimerAdapter getActivity() {
                // return activity instance provided by instrumentation test
                if (timerAdapter == null)
                    timerAdapter = TimerActivityTest.this.getActivity();
                return timerAdapter;
            }
        };
    }

    private static TimerAdapter timerAdapter = null;
    private AbstractTimerActivityTest actualTest;

    public void testActivityCheckTestCaseSetUpProperly() {
        actualTest.testActivityCheckTestCaseSetUpProperly();
    }

    public void testActivityScenarioCancelRunning() throws Throwable {
        actualTest.testActivityScenarioCancelRunning();
    }

    public void testActivityScenarioRepeatIncrementWithin3Seconds() throws Throwable {
        actualTest.testActivityScenarioRepeatIncrementWithin3Seconds();
    }

    public void testActivityScenarioStopAlarming() throws Throwable {
        actualTest.testActivityScenarioStopAlarming();
    }

    public void testActivityScenarioIncUntilFull() throws Throwable {
        actualTest.testActivityScenarioIncUntilFull();
    }

    public void testActivityScenarioSetTimerValue() throws Throwable {
        actualTest.testActivityScenarioSetTimerValue();
    }

    public void testActivityScenarioRotation() throws Throwable {
        actualTest.testActivityScenarioRotation();
    }

    public void testActivityScenarioInit() throws Throwable {
        actualTest.testActivityScenarioInit();
    }
}
