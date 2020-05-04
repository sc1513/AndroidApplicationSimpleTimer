package edu.luc.etl.cs313.android.simpletimer.test.model.alarm;

import org.junit.After;
import org.junit.Before;

import edu.luc.etl.cs313.android.simpletimer.model.alarm.DefaultAlarmModel;



/**
 * Concrete testcase subclass for the default alarm model implementation.
 *
 * @author 413/313 group1
 */
public class DefaultAlarmModelTest extends AbstractAlarmModelTest {

    @Before
    public void setUp() throws Exception {
        setModel(new DefaultAlarmModel());
    }

    @After
    public void tearDown() throws Exception {
        setModel(null);
    }
}