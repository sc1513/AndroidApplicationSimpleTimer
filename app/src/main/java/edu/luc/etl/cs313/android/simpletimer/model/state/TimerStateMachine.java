package edu.luc.etl.cs313.android.simpletimer.model.state;

import edu.luc.etl.cs313.android.simpletimer.common.TimerUIUpdateSource;
import edu.luc.etl.cs313.android.simpletimer.common.TimerUIListener;
import edu.luc.etl.cs313.android.simpletimer.model.clock.OnTickListener;

/**
 * The state machine for the state-based dynamic model of the timer.
 * This interface is part of the State pattern.
 *
 * @author 413/313 group1
 */
public interface TimerStateMachine extends TimerUIListener, OnTickListener, TimerUIUpdateSource, TimerSMStateView { }
