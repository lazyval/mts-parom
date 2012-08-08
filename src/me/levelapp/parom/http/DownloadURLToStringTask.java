package me.levelapp.parom.http;

import android.os.AsyncTask;
import android.util.Log;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.MalformedInputException;

/**
 * @author lazyval
 */
public class DownloadURLToStringTask extends AsyncTask<String, Void, String> {
    private static final String TAG = "Async JSON download";

    @Override
    final public String doInBackground(String... urls) {
        assert (urls.length == 1); // we are downloading only one string
        final String url = urls[0];
        String json = "";
        InputStream in = null;
        try {
            in = new URL(url).openStream();
            json = IOUtils.toString(in);
        } catch (final MalformedInputException e) {
            Log.wtf(TAG, "Malformed JSON events url " + url);
        } catch (final IOException e) {
            // TODO: fallback to the latest successful download
            Log.e(TAG, "Cannot read JSON file from " + url + " Exact problem: " + e.getMessage());
        } finally {
            IOUtils.closeQuietly(in);
        }
        return json;
    }
}
