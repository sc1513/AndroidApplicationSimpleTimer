package edu.luc.etl.cs313.android.simpletimer.test.model.state;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.luc.etl.cs313.android.simpletimer.R;
import edu.luc.etl.cs313.android.simpletimer.common.Constants;
import edu.luc.etl.cs313.android.simpletimer.common.TimerUIUpdateListener;
import edu.luc.etl.cs313.android.simpletimer.model.alarm.AlarmModel;
import edu.luc.etl.cs313.android.simpletimer.model.clock.ClockModel;
import edu.luc.etl.cs313.android.simpletimer.model.clock.OnTickListener;
import edu.luc.etl.cs313.android.simpletimer.model.state.TimerStateMachine;
import edu.luc.etl.cs313.android.simpletimer.model.time.TimeModel;

/**
 * Testcase superclass for the timer state machine model. Unit-tests the state
 * machine in fast-forward mode by directly triggering successive tick events
 * without the presence of a pseudo-real-time clock. Uses a single unified mock
 * object for all dependencies of the state machine model.
 *
 * @author 413/313 group1
 */
public abstract class AbstractTimerStateMachineTest {

    private TimerStateMachine model;

    private UnifiedMockDependency dependency;

    @Before
    public void setUp() throws Exception {
        dependency = new UnifiedMockDependency();
    }

    @After
    public void tearDown() {
        dependency = null;
    }

    /**
     * Setter for dependency injection. Usually invoked by concrete testcase
     * subclass.
     *
     * @param model
     */
    protected void setModel(final TimerStateMachine model) {
        this.model = model;
        if (model == null)
            return;
        this.model.setUIUpdateListener(dependency);
        this.model.actionInit();
    }

    protected UnifiedMockDependency getDependency() {
        return dependency;
    }

    /**
     * Verifies that we're initially in the stopped state (and told the listener
     * about it).
     */
    @Test
    public void testPreconditions() {
        assertEquals(R.string.STOPPED, dependency.getState());
    }

    /**
     * Verifies the following scenario:
     *      time is 0, press button 10 times, expect time is 10
     *      wait 3 seconds, expect time is 10
     *      wait 5 seconds, expect time is 5
     *      press button, expect time is 0
     */
    @Test
    public void testScenarioCancelRunning () {
        int i = 0;
        while ( i < 10) {
            assertTimeEquals(i);
            model.onStartIncrementStop();
            ++i;
        }
        assertTimeEquals(10);
        onTickRepeat(3);
        assertTimeEquals(10);
        onTickRepeat(5);
        assertTimeEquals(5);
        model.onStartIncrementStop();
        assertTimeEquals(0);
    }

    /**
     * Verifies the following scenario:
     *      time is 0, press button 10 times, expect time is 10
     *      wait 2 seconds, press button, expect time is 11
     *      wait 2 seconds, press button,, expect time is 12
     *      wait 2 seconds, expect time is 12
     *      wait 2 second, expect time is 11
     */
    @Test
    public void testActivityScenarioRepeatIncrementWithin3Seconds() {
        int i = 0;
        while ( i < 10) {
            assertTimeEquals(i);
            model.onStartIncrementStop();
            ++i;
        }
        assertTimeEquals(10);
        onTickRepeat(2);
        model.onStartIncrementStop();
        assertTimeEquals(11);
        onTickRepeat(2);
        model.onStartIncrementStop();
        assertTimeEquals(12);
        onTickRepeat(2);
        assertTimeEquals(12);
        onTickRepeat(2);
        assertTimeEquals(11);
    }

    /**
     * Verifies the following scenario:
     *      time is 0, press button 10 times, expect time is 10
     *      wait 3 seconds, expect time is 10
     *      wait 10 seconds, expect time is 0
     *      wait 5 seconds, expect time is 0
     *      press button
     *      wait 5 seconds, expect time is 0
     */
    @Test
    public void testScenarioStopAlarming() {
        int i = 0;
        while ( i < 10) {
            assertTimeEquals(i);
            model.onStartIncrementStop();
            ++i;
        }
        assertTimeEquals(10);
        onTickRepeat(3);
        assertTimeEquals(10);
        onTickRepeat(10);
        assertTimeEquals(0);
        onTickRepeat(5);
        assertTimeEquals(0);
        model.onStartIncrementStop();
        onTickRepeat(5);
        assertTimeEquals(0);
    }

    /**
     * Verifies the following scenario:
     *      time is 0, Set time to 10
     *      press button
     *      wait 10 seconds, expect time is 0
     *  	wait 5 seconds, expect time is 0
     *  	press button
     *  	wait 5 seconds, expect time is 0
     */
    @Test
    public void testScenarioSetTimerValue() {
        assertTimeEquals(0);
        setTimerValue(10);
        assertTimeEquals(10);
        model.onStartIncrementStop();
        onTickRepeat(10);
        assertTimeEquals(0);
        onTickRepeat(5);
        assertTimeEquals(0);
        model.onStartIncrementStop();
        onTickRepeat(5);
        assertTimeEquals(0);
    }

    /**
     * Sends the given number of tick events to the model.
     *
     *  @param n the number of tick events
     */
    protected void onTickRepeat(final int n) {
        for (int i = 0; i < n; i++)
            model.onTick();
    }

    protected void setTimerValue(final int n) {
        dependency.updateTime(n);
    }

    /**
     * Checks whether the model has invoked the expected time-keeping
     * methods on the mock object.
     */
    protected void assertTimeEquals(final int t) {
        assertEquals(t, dependency.getTime());
    }
}

/**
 * Manually implemented mock object that unifies the three dependencies of the
 * timer state machine model. The three dependencies correspond to the three
 * interfaces this mock object implements.
 *
 * @author 413/313 group1
 */
class UnifiedMockDependency implements TimeModel, ClockModel, AlarmModel, TimerUIUpdateListener {

    private int timeValue = -1, stateId = -1;

    private int runningTime = 0, lapTime = -1;

    private int timeoutValue = Constants.TIMEOUT_VALUE;

    private boolean isPlaying = false;

    private boolean started = false;

    public int getTime() {
        return timeValue;
    }

    public int getState() {
        return stateId;
    }

    public boolean getPlayStatus(){ return  isPlaying; }

    public boolean isStarted() {
        return started;
    }

    @Override
    public void setRuntime(int runtime) {
        runningTime = runtime;
    }

    @Override
    public void updateTime(final int timeValue) {
        this.timeValue = timeValue;
    }

    @Override
    public void updateState(final int stateId) {
        this.stateId = stateId;
    }


    @Override
    public void setOnTickListener(OnTickListener listener) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void start() {
        started = true;
    }

    @Override
    public void stop() {
        started = false;
    }

    @Override
    public void resetRuntime() {
        runningTime = 0;
    }

    @Override
    public void incRuntime() {
        if( runningTime >= Constants.SEC_MAX ) {
            return;
        }
        runningTime++;
    }

    @Override
    public int getRuntime() {
        return runningTime;
    }

    @Override
    public void decRuntime() {
        if( runningTime ==0 ) {
            return;
        }
        runningTime--;
    }

    @Override
    public void resetTimeout() {
        timeoutValue = Constants.TIMEOUT_VALUE;
    }

    @Override
    public int decTimeout() {
        if( timeoutValue == 0 ) {
            return 0;
        }
        timeoutValue--;
        return timeoutValue;
    }

    @Override
    public void playSound( boolean isAlarm ){}

    @Override
    public void stopSound(){}

    @Override
    public void setUIUpdateListener(final TimerUIUpdateListener listener){
    }

    @Override
    public void playBeep() {
    }

    @Override
    public void playAlarm() {
        isPlaying = true;
    }

    @Override
    public void stopAlarm() {
        isPlaying = false;
    }

    @Override
    public void disablePlaySound(boolean isDisable) {
    }
}
