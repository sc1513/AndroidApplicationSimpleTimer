package edu.luc.etl.cs313.android.simpletimer.model.time;

import edu.luc.etl.cs313.android.simpletimer.common.Constants;

import static edu.luc.etl.cs313.android.simpletimer.common.Constants.*;

/**
 * An implementation of the timer data model.
 */
public class DefaultTimeModel implements TimeModel {

    private int runningTime = 0;

    private int timeoutTime = TIMEOUT_VALUE;

    private int lapTime = -1;

    @Override
    public void resetRuntime() {
        runningTime = 0;
    }

    @Override
    public void setRuntime(int runtime) {
        runningTime = runtime;
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
    public void resetTimeout() { timeoutTime = TIMEOUT_VALUE; }

    @Override
    public int decTimeout() {
        if( timeoutTime ==0 ) {
            return 0;
        }
        timeoutTime--;
        return timeoutTime;
    }
}