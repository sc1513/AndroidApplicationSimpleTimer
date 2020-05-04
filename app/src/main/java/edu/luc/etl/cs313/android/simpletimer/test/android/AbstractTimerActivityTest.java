package edu.luc.etl.cs313.android.simpletimer.test.android;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import android.content.pm.ActivityInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import edu.luc.etl.cs313.android.simpletimer.R;
import edu.luc.etl.cs313.android.simpletimer.android.TimerAdapter;
import edu.luc.etl.cs313.android.simpletimer.common.Constants;


/**
 * Abstract GUI-level test superclass of several essential timer scenarios.
 *
 * @author 413/313 group1
 *
 */
public abstract class AbstractTimerActivityTest {
    /**
     * Verifies that the activity under test can be launched.
     */
    @Test
    public void testActivityCheckTestCaseSetUpProperly() {
        assertNotNull("activity should be launched successfully", getActivity());
    }

    /**
     * Verifies the following scenario: time is 0, expect state display is Stopped, expect button display "Set"
     *
     * @throws Throwable
     */
    @Test
    public void testActivityScenarioInit() throws Throwable {
        getActivity().runOnUiThread(() -> {
            assertEquals(0, getDisplayedValue());
            assertEquals( "Stopped", getDisplayedState());
            assertEquals( "Start", getDisplayedButton() );
        });
    }

    /**
     * Verifies the following scenario:
     *      time is 0, expect state display is Stopped, expect button display "Start"
     *      press button, expect state display is Increment, expect button display "Increment"
     *      press button 9 times, every time expect time display adding 1
     *      wait 3 seconds, expect time display is 10,expect state display is Running,expect button display "Cancel"
     *      wait 5 seconds, expect time display is 5
     *      press button, expect time display is 0 and state display is Stopped,expect button display "Start"
     *
     * @throws Throwable
     */
    @Test
    public void testActivityScenarioCancelRunning() throws Throwable {
        getActivity().runOnUiThread(() -> {
            assertEquals(0, getDisplayedValue());
            assertEquals( "Stopped", getDisplayedState());
            assertEquals( "Start", getDisplayedButton() );
            assertTrue(getStartIncrementStopButton().performClick());
        });
        runUiThreadTasks();

        int i = 1;
        while ( i < 10) {
            Thread.sleep(500); // <-- do not run this in the UI thread!
            runUiThreadTasks();
            assertEquals( "Increment", getDisplayedState());
            assertEquals( "Increment", getDisplayedButton() );
            assertEquals(i, getDisplayedValue());
            getActivity().runOnUiThread(() -> {
                assertTrue(getStartIncrementStopButton().performClick());
            });
            runUiThreadTasks();
            ++i;
        }
        Thread.sleep(3000); // <-- do not run this in the UI thread!
        runUiThreadTasks();
        getActivity().runOnUiThread(() -> {
            assertEquals(10, getDisplayedValue());
            assertEquals( "Cancel", getDisplayedButton() );
            assertEquals( "Running", getDisplayedState());
        });
        Thread.sleep(5000); // <-- do not run this in the UI thread!
        runUiThreadTasks();
        getActivity().runOnUiThread(() -> {
            assertEquals(5, getDisplayedValue());
            assertTrue(getStartIncrementStopButton().performClick());
        });
        runUiThreadTasks();
        getActivity().runOnUiThread(() -> {
            assertEquals(0, getDisplayedValue());
            assertEquals( "Start", getDisplayedButton() );
            assertEquals( "Stopped", getDisplayedState());
        });
    }

    /**
     * Verifies the following scenario:
     *      time is 0, expect state display is Stopped, expect button display "Start"
     *      press button, expect state display is Increment, expect button display "Increment"
     *      press button 9 times, every time expect time display adding 1
     *      wait 2 seconds, press button, expect time display is 11,expect state display is Increment
     *      wait 2 seconds, press button, expect time display is 12,expect state display is Increment
     *      wait 3 seconds, expect time display is 12, expect state display is Running,expect button display "Cancel"
     *
     * @throws Throwable
     */
    @Test
    public void testActivityScenarioRepeatIncrementWithin3Seconds() throws Throwable {
        getActivity().runOnUiThread(() -> {
            assertEquals(0, getDisplayedValue());
            assertEquals( "Stopped", getDisplayedState());
            assertEquals( "Start", getDisplayedButton() );
            assertTrue(getStartIncrementStopButton().performClick());
        });
        runUiThreadTasks();

        int i = 1;
        while ( i < 10) {
            Thread.sleep(500); // <-- do not run this in the UI thread!
            runUiThreadTasks();
            assertEquals( "Increment", getDisplayedState());
            assertEquals( "Increment", getDisplayedButton() );
            assertEquals(i, getDisplayedValue());
            getActivity().runOnUiThread(() -> {
                assertTrue(getStartIncrementStopButton().performClick());
            });
            runUiThreadTasks();
            ++i;
        }
        Thread.sleep(2000); // <-- do not run this in the UI thread!
        runUiThreadTasks();
        getActivity().runOnUiThread(() -> {
            assertTrue(getStartIncrementStopButton().performClick());});
        runUiThreadTasks();
        getActivity().runOnUiThread(() -> {
            assertEquals(11, getDisplayedValue());
            assertEquals( "Increment", getDisplayedState());
        });
        Thread.sleep(2000); // <-- do not run this in the UI thread!
        runUiThreadTasks();
        getActivity().runOnUiThread(() -> {
            assertTrue(getStartIncrementStopButton().performClick());});
        runUiThreadTasks();
        getActivity().runOnUiThread(() -> {
            assertEquals(12, getDisplayedValue());
            assertEquals( "Increment", getDisplayedState());
        });

        Thread.sleep(3000); // <-- do not run this in the UI thread!
        runUiThreadTasks();

        getActivity().runOnUiThread(() -> {
            assertEquals(12, getDisplayedValue());
            assertEquals( "Cancel", getDisplayedButton() );
            assertEquals( "Running", getDisplayedState());
        });
        runUiThreadTasks();

    }

    /**
     * Verifies the following scenario:
     *      time is 0, expect state display is Stopped, expect button display "Start"
     *      press button, expect state display is Increment, expect button display "Increment"
     *      press button 9 times, everytime expect time display adding 1
     *      wait 3 seconds, expect time display is 10,expect state display is Running,expect button display "Cancel"
     *      wait 10 seconds, expect time display is 0, expect state display is Alarming,expect button display "Stop"
     *      wait 5 seconds, expect state display is Alarming
     *      press button, expect time display is 0 and state display is Stopped,expect button display "Start"
     *
     * @throws Throwable
     */
    @Test
    public void testActivityScenarioStopAlarming() throws Throwable {
        getActivity().runOnUiThread(() -> {
            assertEquals(0, getDisplayedValue());
            assertEquals( "Stopped", getDisplayedState());
            assertEquals( "Start", getDisplayedButton() );
            assertTrue(getStartIncrementStopButton().performClick());
        });
        runUiThreadTasks();

        int i = 1;
        while ( i < 10) {
            Thread.sleep(500); // <-- do not run this in the UI thread!
            runUiThreadTasks();
            assertEquals( "Increment", getDisplayedState());
            assertEquals( "Increment", getDisplayedButton() );
            assertEquals(i, getDisplayedValue());
            getActivity().runOnUiThread(() -> {
                assertTrue(getStartIncrementStopButton().performClick());
            });
            runUiThreadTasks();
            ++i;
        }
        Thread.sleep(3000); // <-- do not run this in the UI thread!
        runUiThreadTasks();

        getActivity().runOnUiThread(() -> {
            assertEquals(10, getDisplayedValue());
            assertEquals( "Cancel", getDisplayedButton() );
            assertEquals( "Running", getDisplayedState());
        });
        Thread.sleep(10500); // <-- do not run this in the UI thread!
        runUiThreadTasks();
        getActivity().runOnUiThread(() -> {
            assertEquals(0, getDisplayedValue());
            assertEquals( "Stop", getDisplayedButton() );
            assertEquals( "Alarming", getDisplayedState());
        });
        Thread.sleep(5000); // <-- do not run this in the UI thread!
        runUiThreadTasks();
        getActivity().runOnUiThread(() -> {
            assertEquals( "Alarming", getDisplayedState());
            assertTrue(getStartIncrementStopButton().performClick());
        });
        runUiThreadTasks();
        getActivity().runOnUiThread(() -> {
            assertEquals(0, getDisplayedValue());
            assertEquals( "Start", getDisplayedButton() );
            assertEquals( "Stopped", getDisplayedState());
        });
    }

    /**
     * Verifies the following scenario:
     *      display is 0, press button 99 times,
     *      expect time display is 99,expect state display is Running,expect button display "Cancel"
     */
    @Test
    public void testActivityScenarioIncUntilFull() throws Throwable {
        getActivity().runOnUiThread(() -> {
            assertEquals(0, getDisplayedValue());
            assertEquals( "Stopped", getDisplayedState());
            assertEquals( "Start", getDisplayedButton() );
            assertTrue(getStartIncrementStopButton().performClick());
        });
        runUiThreadTasks();

        int i = 1;
        while (i < Constants.SEC_MAX) {
            Thread.sleep(500); // <-- do not run this in the UI thread!
            runUiThreadTasks();
            assertEquals( "Increment", getDisplayedState());
            assertEquals( "Increment", getDisplayedButton() );
            assertEquals(i, getDisplayedValue());
            getActivity().runOnUiThread(() -> {
                assertTrue(getStartIncrementStopButton().performClick());
            });
            runUiThreadTasks();
            ++i;
        }
        Thread.sleep(1000); // <-- do not run this in the UI thread!
        runUiThreadTasks();
        getActivity().runOnUiThread(() -> {
            assertEquals( Constants.SEC_MAX - 1, getDisplayedValue());
            assertEquals( "Cancel", getDisplayedButton() );
            assertEquals( "Running", getDisplayedState());
        });
    }

    /**
     * Verifies the following scenario:
     *      Set display value 10, press button,
     *      expect time display is 10,expect state display is Running,expect button display "Cancel"
     *      wait 10 seconds, expect time display is 0, expect state display is Alarming,expect button display "Stop"
     *      wait 5 seconds, expect state display is Alarming
     *      press button, expect time display is 0 and state display is Stopped,expect button display "Start"
     */
    @Test
    public void testActivityScenarioSetTimerValue() throws Throwable {
        getActivity().runOnUiThread(() -> {
            assertEquals(0, getDisplayedValue());
            assertEquals( "Stopped", getDisplayedState());
            assertEquals( "Start", getDisplayedButton() );
            setDisplayedValue("10");
        });
        runUiThreadTasks();
        getActivity().runOnUiThread(() -> {
            assertTrue(getStartIncrementStopButton().performClick());
        });
        runUiThreadTasks();
        getActivity().runOnUiThread(() -> {
            assertEquals( 10, getDisplayedValue());
            assertEquals( "Cancel", getDisplayedButton() );
            assertEquals( "Running", getDisplayedState());
        });
        Thread.sleep(10500); // <-- do not run this in the UI thread!
        runUiThreadTasks();
        getActivity().runOnUiThread(() -> {
            assertEquals(0, getDisplayedValue());
            assertEquals( "Stop", getDisplayedButton() );
            assertEquals( "Alarming", getDisplayedState());
        });
        Thread.sleep(5000); // <-- do not run this in the UI thread!
        runUiThreadTasks();
        getActivity().runOnUiThread(() -> {
            assertEquals( "Alarming", getDisplayedState());
            assertTrue(getStartIncrementStopButton().performClick());});
        runUiThreadTasks();
        getActivity().runOnUiThread(() -> {
            assertEquals(0, getDisplayedValue());
            assertEquals( "Start", getDisplayedButton() );
            assertEquals( "Stopped", getDisplayedState());
        });
    }

    // begin-method-testActivityScenarioRotation
    //checks that the displayed value (state, button, and time) remains the same if the device is rotated
    @Test
    public void testActivityScenarioRotation() throws Throwable{
        getActivity().runOnUiThread(() -> {
            assertEquals(0, getDisplayedValue());
            assertEquals( "Stopped", getDisplayedState());
            assertEquals( "Start", getDisplayedButton() );
            assertTrue(getStartIncrementStopButton().performClick());
        });
        runUiThreadTasks();

        int i = 1;
        while ( i < 10) {
            Thread.sleep(500); // <-- do not run this in the UI thread!
            runUiThreadTasks();
            assertEquals(i, getDisplayedValue());
            assertEquals( "Increment", getDisplayedState());
            assertEquals( "Increment", getDisplayedButton() );
            getActivity().runOnUiThread(() -> {
                assertTrue(getStartIncrementStopButton().performClick());
            });
            runUiThreadTasks();
            ++i;
        }

        getActivity().runOnUiThread(() -> {
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        });
        runUiThreadTasks();
        Thread.sleep(4000); // <-- do not run this in the UI thread!
        runUiThreadTasks();
        assertEquals( "Cancel", getDisplayedButton() );
        assertEquals( "Running", getDisplayedState());

        getActivity().runOnUiThread(() -> {
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            assertTrue(getStartIncrementStopButton().performClick());
        });
        runUiThreadTasks();
        Thread.sleep(3000); // <-- do not run this in the UI thread!
        runUiThreadTasks();
        assertEquals(0, getDisplayedValue());
        assertEquals( "Start", getDisplayedButton() );
        assertEquals( "Stopped", getDisplayedState());
    }


    // auxiliary methods for easy access to UI widgets

    protected abstract TimerAdapter getActivity();


    protected int tvToInt(final TextView t) {
        return Integer.parseInt(t.getText().toString().trim());
    }

    protected int getDisplayedValue() {
        final EditText ts = getActivity().findViewById(R.id.seconds);
        return tvToInt(ts);
    }

    protected void setDisplayedValue(String value ) {
        final EditText ts = getActivity().findViewById(R.id.seconds);
        ts.setText(value);
    }

    protected String getDisplayedState() {
        final TextView ts = getActivity().findViewById(R.id.stateName);
        return ts.getText().toString();
    }

    protected String getDisplayedButton() {
        final Button bt = getActivity().findViewById(R.id.startIncrementStop);
        return bt.getText().toString();
    }

    protected Button getStartIncrementStopButton() {
        return (Button) getActivity().findViewById(R.id.startIncrementStop);
    }


    /**
     * Explicitly runs tasks scheduled to run on the UI thread in case this is required
     * by the testing framework, e.g., Robolectric.
     */
    protected void runUiThreadTasks() {

    }

}
