package edu.luc.etl.cs313.android.simpletimer.common;

/**
 * A source of UI update events for the timer.
 * This interface is typically implemented by the model.
 *
 * @author 413/313 group1
 */
public interface TimerUIUpdateSource {
    void setUIUpdateListener(TimerUIUpdateListener listener);
}
