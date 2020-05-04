package edu.luc.etl.cs313.android.simpletimer.model.alarm;


import edu.luc.etl.cs313.android.simpletimer.common.TimerUIUpdateListener;


/**
 * The model of playing sound for alarm.
 *
 * @author 413/313 group1
 */
public interface AlarmModel {
    void setUIUpdateListener(final TimerUIUpdateListener listener);
    void playBeep();
    void playAlarm();
    void stopAlarm();
}
