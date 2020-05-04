package edu.luc.etl.cs313.android.simpletimer.model.state;

import edu.luc.etl.cs313.android.simpletimer.common.TimerUIListener;
import edu.luc.etl.cs313.android.simpletimer.model.clock.OnTickListener;

/**
 * A state in a state machine. This interface is part of the State pattern.
 *
 * @author 413/313 group1
 */
interface TimerState extends TimerUIListener, OnTickListener {
    void updateView();
    int getId();
}
