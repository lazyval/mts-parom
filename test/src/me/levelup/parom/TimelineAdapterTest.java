package me.levelup.parom;

import android.test.AndroidTestCase;
import junit.framework.Assert;
import me.levelapp.parom.TimelineAdapter;

import java.io.File;

public class TimelineAdapterTest extends AndroidTestCase {

    public void setUp() throws Exception {
        final File jsonFile = new File("/src/res/events.json");
        if(!jsonFile.exists()) throw new RuntimeException("Not found JSON file for tests: check if there is such!");
    }

    public void testReadEvents() throws Exception {
        final TimelineAdapter adapter = new TimelineAdapter();
        Assert.assertTrue(adapter != null);
    }
}
