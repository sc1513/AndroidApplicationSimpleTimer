package edu.luc.etl.cs313.android.simpletimer.model;

import edu.luc.etl.cs313.android.simpletimer.common.TimerUIUpdateListener;
import edu.luc.etl.cs313.android.simpletimer.model.alarm.AlarmModel;
import edu.luc.etl.cs313.android.simpletimer.model.alarm.DefaultAlarmModel;
import edu.luc.etl.cs313.android.simpletimer.model.clock.ClockModel;
import edu.luc.etl.cs313.android.simpletimer.model.clock.DefaultClockModel;
import edu.luc.etl.cs313.android.simpletimer.model.state.DefaultTimerStateMachine;
import edu.luc.etl.cs313.android.simpletimer.model.state.TimerStateMachine;
import edu.luc.etl.cs313.android.simpletimer.model.time.DefaultTimeModel;
import edu.luc.etl.cs313.android.simpletimer.model.time.TimeModel;

/**
 * An implementation of the model facade.
 *
 * @author 413/313 group1
 */
public class ConcreteTimerModelFacade implements TimerModelFacade {

    private TimerStateMachine stateMachine;

    private ClockModel clockModel;

    private TimeModel timeModel;

    private AlarmModel alarmModel;

    public ConcreteTimerModelFacade() {
        timeModel = new DefaultTimeModel();
        clockModel = new DefaultClockModel();
        alarmModel = new DefaultAlarmModel();
        stateMachine = new DefaultTimerStateMachine(timeModel, clockModel, alarmModel);
        clockModel.setOnTickListener(stateMachine);
    }

    @Override
    public void onStart() {
        stateMachine.actionInit();
    }

    @Override
    public void setUIUpdateListener(final TimerUIUpdateListener listener) {
        stateMachine.setUIUpdateListener(listener);
    }

    @Override
    public void onStartIncrementStop() {
        stateMachine.onStartIncrementStop();
    }

}
