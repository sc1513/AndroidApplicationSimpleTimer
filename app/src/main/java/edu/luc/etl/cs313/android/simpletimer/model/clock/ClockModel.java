package edu.luc.etl.cs313.android.simpletimer.model.clock;

/**
 * The active model of the internal clock that periodically emits tick events.
 *
 * @author 413/313 group1
 */
public interface ClockModel extends OnTickSource {
    void start();
    void stop();
}
