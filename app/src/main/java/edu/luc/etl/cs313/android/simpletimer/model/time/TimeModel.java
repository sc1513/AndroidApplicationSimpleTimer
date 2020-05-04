package edu.luc.etl.cs313.android.simpletimer.model.time;

/**
 * The passive data model of the timer.
 * It does not emit any events.
 *
 * @author 413/313 group1
 */
public interface TimeModel {
    void resetRuntime();
    void incRuntime();
    void decRuntime();
    int getRuntime();
    void resetTimeout();
    int decTimeout();
    void setRuntime(int runtime);
}
