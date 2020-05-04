package edu.luc.etl.cs313.android.simpletimer.model.state;

import edu.luc.etl.cs313.android.simpletimer.R;

class AlarmingState implements TimerState {

    public AlarmingState(final TimerSMStateView sm) {
        this.sm = sm;
    }

    private final TimerSMStateView sm;

    /**
     * Alarming: onClick / timer = MIN, stopTimer, stopAlarm  -> Stopped
     */
    @Override
    public void onStartIncrementStop() {
        sm.actionStopAlarm();
        sm.actionStopTimer();
        sm.actionStop();
        sm.toStoppedState();
    }

    @Override
    public void onTick() {

    }

    @Override
    public void updateView() {
        sm.updateUIRuntime();
    }

    @Override
    public int getId() {
        return R.string.ALARMING;
    }
}
