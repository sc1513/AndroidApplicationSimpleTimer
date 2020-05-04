package edu.luc.etl.cs313.android.simpletimer.model.clock;

/**
 * A source of onTick events for the timer.
 * This interface is typically implemented by the model.
 *
 * @author 413/313 group1
 */
public interface OnTickSource {
    void setOnTickListener(OnTickListener listener);
}
