package me.levelapp.parom;

import android.test.AndroidTestCase;
import junit.framework.Assert;

import java.io.File;


public class TimelineAdapterTest extends AndroidTestCase {
    private static final String TEST_JSON = "./raw/events.json";

    public void setUp() throws Exception {
        final File jsonFile = new File(TEST_JSON);
        if (!jsonFile.exists()) throw new RuntimeException("Not found JSON file for tests: check if there is such!");
    }

    public void testReadEvents() throws Exception {
        final TimelineAdapter adapter = new TimelineAdapter(TEST_JSON);
        Assert.assertNotNull("Adapter has to be initialized properly (no exceptions or asserts).", adapter);
    }
}
