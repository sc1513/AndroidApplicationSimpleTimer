package edu.luc.etl.cs313.android.simpletimer.model.state;

/**
 * The restricted view states have of their surrounding state machine.
 * This is a client-specific interface in Peter Coad's terminology.
 *
 * @author 413/313 group1
 */
interface TimerSMStateView {

    // transitions
    void toRunningState();
    void toStoppedState();
    void toIncrementState();
    void toAlarmingState();

    // actions
    void actionInit();
    void actionReset();
    void actionStart();
    void actionStop();
    void actionInc();
    void actionDec();
    void actionResetTimeout();
    void actionTimeoutDec();
    void actionStartTimer();
    void actionStopTimer();
    void actionUpdateView();
    void actionStopAlarm();

    // state-dependent UI updates
    void updateUIRuntime();

    //TimerState getState();
}
