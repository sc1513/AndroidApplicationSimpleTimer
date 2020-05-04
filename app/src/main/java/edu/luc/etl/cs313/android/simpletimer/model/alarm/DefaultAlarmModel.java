package edu.luc.etl.cs313.android.simpletimer.model.alarm;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;



import java.io.IOException;

import edu.luc.etl.cs313.android.simpletimer.common.Constants;
import edu.luc.etl.cs313.android.simpletimer.common.TimerUIUpdateListener;
import edu.luc.etl.cs313.android.simpletimer.model.clock.OnTickListener;

import static edu.luc.etl.cs313.android.simpletimer.common.Constants.*;

/**
 * An implementation of the timer alarm model.
 */
public class DefaultAlarmModel implements AlarmModel {

    private TimerUIUpdateListener listener;

    @Override
    public void setUIUpdateListener(final TimerUIUpdateListener listener) {
        this.listener = listener;
    }

    @Override
    public void playBeep() {
        listener.playSound( false );
    }

    @Override
    public void playAlarm() {
        listener.playSound( true );
    }

    @Override
    public void stopAlarm() {
        listener.stopSound();
    }
}