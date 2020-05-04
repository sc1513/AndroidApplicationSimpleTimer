package edu.luc.etl.cs313.android.simpletimer.model;

import edu.luc.etl.cs313.android.simpletimer.common.TimerUIUpdateSource;
import edu.luc.etl.cs313.android.simpletimer.common.TimerUIListener;


/**
 * A thin model facade. Following the Facade pattern,
 * this isolates the complexity of the model from its clients (usually the adapter).
 *
 * @author 413/313 group1
 */
public interface TimerModelFacade extends TimerUIListener, TimerUIUpdateSource {
    void onStart();
}
