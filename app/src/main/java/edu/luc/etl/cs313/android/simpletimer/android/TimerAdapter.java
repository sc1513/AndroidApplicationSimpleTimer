package edu.luc.etl.cs313.android.simpletimer.android;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;

import edu.luc.etl.cs313.android.simpletimer.R;
import edu.luc.etl.cs313.android.simpletimer.common.Constants;
import edu.luc.etl.cs313.android.simpletimer.common.TimerUIUpdateListener;
import edu.luc.etl.cs313.android.simpletimer.model.ConcreteTimerModelFacade;
import edu.luc.etl.cs313.android.simpletimer.model.TimerModelFacade;
import edu.luc.etl.cs313.android.simpletimer.model.alarm.AlarmModel;

import static edu.luc.etl.cs313.android.simpletimer.common.Constants.SEC_MAX;

import android.content.res.Configuration;
import android.widget.Toast;

/**
 * A thin adapter component for the timer.
 *
 * @author 413/313 group1
 */
public class TimerAdapter extends Activity implements TimerUIUpdateListener{

    private static String TAG = "timer-android-activity";

    /*
    private int time = 0;
    private int stateId = R.string.STOPPED;
    */

    /**
     * The state-based dynamic model.
     */
    private TimerModelFacade model;

    protected void setModel(final TimerModelFacade model) {
        this.model = model;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // inject dependency on view so this adapter receives UI events
        setContentView(R.layout.activity_main);
        // inject dependency on model into this so model receives UI events
        this.setModel(new ConcreteTimerModelFacade());
        // inject dependency on this into model to register for UI updates
        model.setUIUpdateListener(this);
        model.onStart();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    /**
     * Override onConfigurationChanged to handle rotation.
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Toast.makeText( this, "landscape",Toast.LENGTH_SHORT).show();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            Toast.makeText( this, "portrait",Toast.LENGTH_SHORT).show();
        }
    }

    /*
    @Override
    protected void onStart() {
        super.onStart();
        restoreModelFromPrefs();
        model.onStart(time, stateId);
    }


    @Override
    protected void onStop() {
        super.onStop();
        saveModelToPrefs();
    }
    */

    /**
     * Updates the timer value in the UI.
     * @param time
     */
    public void updateTime(final int time) {
        // UI adapter responsibility to schedule incoming events on UI thread
        runOnUiThread(() -> {
            final EditText etS = (EditText) findViewById(R.id.seconds);
            int seconds = time ;
            if( time > Constants.SEC_MAX) {
                seconds = Constants.SEC_MAX;
            }
            etS.setText(Integer.toString(seconds / 10) + Integer.toString(seconds % 10));
            //this.time = time;
        });
    }

    /**
     * Get timer value from EditText.
     */
    @Override
    public int getTime() {
        final EditText ets = (EditText)findViewById(R.id.seconds);
        int seconds = Constants.SEC_MIN;
        try {
            seconds = Integer.parseInt(ets.getText().toString().trim());
            if( seconds < Constants.SEC_MIN ) {
                seconds = Constants.SEC_MIN;
            } else if( seconds > SEC_MAX ){
                seconds = SEC_MAX;
            }
        } catch (final NumberFormatException ex) {
            seconds = Constants.SEC_MIN;
        }
        ets.setText(Integer.toString(seconds));
        //this.time = seconds;
        return seconds;
    }

    //disable editable text
    private void disableEditText() {
        final EditText editText = (EditText) findViewById(R.id.seconds);
        editText.setEnabled(false);
        editText.setFocusable(false);
        editText.setFocusableInTouchMode(false);
        editText.setBackgroundColor(android.R.color.transparent);
    }

    //enable editable text
    private void enableEditText() {
        final EditText editText = (EditText) findViewById(R.id.seconds);
        editText.setFocusableInTouchMode(true);
        editText.setFocusable(true);
        editText.setEnabled(true);
        editText.setCursorVisible(true);
        editText.setBackgroundColor(android.R.color.primary_text_dark);
    }

    /**
     * Updates the state name in the UI.
     * @param stateId
     */
    public void updateState(final int stateId) {
        // UI adapter responsibility to schedule incoming events on UI thread
        runOnUiThread(() -> {
            final TextView stateName = (TextView) findViewById(R.id.stateName);
            stateName.setText(getString(stateId));

            //this.stateId = stateId;

            //set Button display text
            final Button commandButton = (Button) findViewById(R.id.startIncrementStop);

            if( stateId == R.string.STOPPED ) {
                commandButton.setText(getString(R.string.START));
                //editable only when the timer is stopped
                enableEditText();
            }
            else {
                //disable when the timer isn't stopped
                disableEditText();
                if( stateId == R.string.ALARMING ) {
                    commandButton.setText(getString(R.string.STOP));
                }
                else if( stateId == R.string.INCREMENT ) {
                    commandButton.setText(getString(R.string.INCREMENT));
                }
                else {
                    commandButton.setText(getString(R.string.CANCEL));
                }
            }

        });
    }


    // forward event listener methods to the model
    public void onStartIncrementStop(final View view) {
        model.onStartIncrementStop();
    }

    private MediaPlayer mediaPlayer;
    private boolean disablePlaysound = false;
    /**
     * Plays sound: beep or alarm.
     * @param isAlarm
     */
    @Override
    public void playSound( boolean isAlarm ) {
        if(disablePlaysound)
            return;

        final Uri defaultRingtoneUri = RingtoneManager.getDefaultUri(
                isAlarm ? RingtoneManager.TYPE_ALARM : RingtoneManager.TYPE_NOTIFICATION);
        mediaPlayer = new MediaPlayer();
        final Context context = getApplicationContext();

        try {
            mediaPlayer.setDataSource(context, defaultRingtoneUri);
            /*
            mediaPlayer.setAudioStreamType(
                    isAlarm? AudioManager.STREAM_ALARM: AudioManager.STREAM_NOTIFICATION);

            */
            mediaPlayer.prepare();
            mediaPlayer.setOnCompletionListener(MediaPlayer::release);
            mediaPlayer.start();
        } catch (final IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void stopSound() {
        if(disablePlaysound)
            return;
        mediaPlayer.stop();
        mediaPlayer.release();
    }

    @Override
    public void disablePlaySound(boolean isDisable) {
        disablePlaysound = isDisable;
    }
/*
    @Override
    protected void onSaveInstanceState( Bundle outState) {
        super.onSaveInstanceState( outState );

        outState.putInt( "time", time );
        outState.putInt( "stateId", stateId );
    }

    @Override
    protected void onRestoreInstanceState( Bundle savedInstanceState ) {
        super.onRestoreInstanceState( savedInstanceState );

        time = savedInstanceState.getInt("time");
        stateId = savedInstanceState.getInt("stateId");
    }
*/
    /** Attempts to read the externally saved counter value and update the model. */
    /*
    protected void restoreModelFromPrefs() {
        final SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        sharedPref.getInt("time",this.time);
        sharedPref.getInt("stateId", this.stateId);
    }
    */
    /** Saves the counter value externally. */
    /*
    protected void saveModelToPrefs() {
        final SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("time", time);
        editor.putInt("stateId", stateId );
        editor.commit();
    }
    */


}
