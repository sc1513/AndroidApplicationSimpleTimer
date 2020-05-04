package edu.luc.etl.cs313.android.simpletimer.model.state;

import edu.luc.etl.cs313.android.simpletimer.common.Constants;
import edu.luc.etl.cs313.android.simpletimer.common.TimerUIUpdateListener;
import edu.luc.etl.cs313.android.simpletimer.model.alarm.AlarmModel;
import edu.luc.etl.cs313.android.simpletimer.model.clock.ClockModel;
import edu.luc.etl.cs313.android.simpletimer.model.time.TimeModel;

/**
 * An implementation of the state machine for the timer.
 *
 * @author 413/313 group1
 */
public class DefaultTimerStateMachine implements TimerStateMachine {

    public DefaultTimerStateMachine(final TimeModel timeModel,
                                    final ClockModel clockModel,
                                    final AlarmModel alarmModel) {
        this.timeModel = timeModel;
        this.clockModel = clockModel;
        this.alarmModel = alarmModel;
    }

    private final TimeModel timeModel;

    private final ClockModel clockModel;

    private AlarmModel alarmModel;

    /**
     * The internal state of this adapter component. Required for the State pattern.
     */
    private TimerState state;

    protected void setState(final TimerState state) {
        this.state = state;
        uiUpdateListener.updateState(state.getId());
    }

    /*
    @Override
    public TimerState getState()
    {
        return state;
    }
    */


    private TimerUIUpdateListener uiUpdateListener;

    @Override
    public void setUIUpdateListener(final TimerUIUpdateListener uiUpdateListener) {
        this.uiUpdateListener = uiUpdateListener;
        this.alarmModel.setUIUpdateListener( uiUpdateListener );
    }

    // forward event uiUpdateListener methods to the current state
    // these must be synchronized because events can come from the
    // UI thread or the timer thread
    @Override public synchronized void onStartIncrementStop() { state.onStartIncrementStop(); }
    @Override public synchronized void onTick()      { state.onTick(); }

    @Override public void updateUIRuntime() {
        uiUpdateListener.updateTime(timeModel.getRuntime());
    }

    // known states
    private final TimerState STOPPED     = new StoppedState(this);
    private final TimerState RUNNING     = new RunningState(this);
    private final TimerState INCREMENT = new IncrementState(this);
    private final TimerState ALARMING = new AlarmingState(this);

    // transitions
    @Override public void toRunningState()    {
        alarmModel.playBeep();
        setState(RUNNING);
    }
    @Override public void toStoppedState()    {  setState(STOPPED);}
    @Override public void toIncrementState() { setState(INCREMENT); }
    @Override public void toAlarmingState() {  setState(ALARMING);}

    // actions
    @Override public void actionInit()       { toStoppedState(); actionStop(); }
    @Override public void actionReset()      { actionStop(); }


    @Override public void actionStart(){
        /**Get timer(2-digit editable text area, editable only when the timer is stopped) value
        /*if the value is 0, go to increment state. otherwise, timer is set and go to running state
         */
        int time = uiUpdateListener.getTime();
        clockModel.start();
        if( time != Constants.SEC_MIN)
        {
            timeModel.setRuntime( time );
            toRunningState();
        }
        else {
            /**
             * timer++, timeout=WAITTIME,startTimer -> Increment
             */
            actionInc();
            actionResetTimeout();
            toIncrementState();
        }
    }

    @Override public void actionStop()       {
        timeModel.resetRuntime();
        actionUpdateView();
    }

    /**
     * timer--; [timer == MIN] / startAlarm -> Alarming
     */
    @Override public void actionDec(){
        timeModel.decRuntime();
        if( timeModel.getRuntime() == Constants.SEC_MIN )
        {
            toAlarmingState();
            alarmModel.playAlarm();
        }
        actionUpdateView();
    }

    /**
     * Increment: onClick / timeout=WAITTIME,timer++; [timer == MAX] / beep -> Running
     */
    @Override public void actionInc() {
        timeModel.incRuntime();
        if( timeModel.getRuntime() == Constants.SEC_MAX ) {
            toRunningState();
        }
        actionUpdateView();
    }

    @Override public void actionResetTimeout() { timeModel.resetTimeout(); }

    /**
     * Increment: onTick / timeout--; [timeout == 0] / beep -> Runing
     */
    @Override public void actionTimeoutDec() {
        int timeout = timeModel.decTimeout();
        if( timeout == 0 )
        {
            toRunningState();
        }
    }
    @Override public void actionStartTimer() { clockModel.start(); }
    @Override public void actionStopTimer()  { clockModel.stop();  }
    @Override public void actionStopAlarm()  { alarmModel.stopAlarm(); }
    @Override public void actionUpdateView() { state.updateView(); }
}
