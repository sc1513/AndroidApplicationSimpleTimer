package edu.luc.etl.cs313.android.simpletimer.model.state;

import edu.luc.etl.cs313.android.simpletimer.R;

class StoppedState implements TimerState {

    public StoppedState(final TimerSMStateView sm) {
        this.sm = sm;
    }

    private final TimerSMStateView sm;


    /**
     * Stopped: onClick/ timer++, timeout=WAITTIME,startTimer -> Increment
     */
    @Override
    public void onStartIncrementStop() {
        sm.actionStart();
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
        return R.string.STOPPED;
    }
}
