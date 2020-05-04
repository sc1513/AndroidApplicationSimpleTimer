package edu.luc.etl.cs313.android.simpletimer.common;

/**
 * A listener for UI update notifications.
 * This interface is typically implemented by the adapter, with the
 * notifications coming from the model.
 *
 * @author 413/313 group1
 */
public interface TimerUIUpdateListener {
    void updateTime(int timeValue);
    void updateState(int stateId);
    void playSound( boolean isAlarm );
    void stopSound();
    void disablePlaySound(boolean isDisable);
    int getTime();
}
