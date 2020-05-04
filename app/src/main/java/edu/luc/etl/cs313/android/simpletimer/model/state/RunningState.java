package edu.luc.etl.cs313.android.simpletimer.model.state;

import edu.luc.etl.cs313.android.simpletimer.R;

class RunningState implements TimerState {

    public RunningState(final TimerSMStateView sm) {
        this.sm = sm;
    }

    private final TimerSMStateView sm;

    /**
     * Running: onClick / timer = MIN, stopTimer -> Stopped
     */
    @Override
    public void onStartIncrementStop() {
        sm.actionStop();
        sm.actionStopTimer();
        sm.toStoppedState();
    }

    /**
     * Running: onTick / timer--; [timer == MIN] / startAlarm -> Alarming
     */
    @Override
    public void onTick() {
        sm.actionDec();
    }

    @Override
    public void updateView() {
        sm.updateUIRuntime();
    }

    @Override
    public int getId() {
        return R.string.RUNNING;
    }
}
