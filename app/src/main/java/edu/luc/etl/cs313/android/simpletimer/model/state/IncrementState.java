package edu.luc.etl.cs313.android.simpletimer.model.state;

import edu.luc.etl.cs313.android.simpletimer.R;

class IncrementState implements TimerState {

    public IncrementState(final TimerSMStateView sm) {
        this.sm = sm;
    }

    private final TimerSMStateView sm;

    /**
     * Increment: onClick / timeout=WAITTIME,timer++; [timer == MAX] / beep -> Running
     */
    @Override
    public void onStartIncrementStop() {
        sm.actionResetTimeout();
        sm.actionInc();
    }

    /**
     * Increment: onTick / timeout--; [timeout == 0] / beep -> Runing
     */
    @Override
    public void onTick() {
        sm.actionTimeoutDec();

    }

    @Override
    public void updateView() {
        sm.updateUIRuntime();
    }

    @Override
    public int getId() {
        return R.string.INCREMENT;
    }
}
