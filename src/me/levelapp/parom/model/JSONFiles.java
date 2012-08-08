package me.levelapp.parom.model;

import android.content.Context;
import com.google.common.io.CharStreams;
import com.google.common.io.OutputSupplier;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * User: anatoly
 * Date: 07.08.12
 * Time: 16:29
 */
public class JSONFiles
{
    public static final String PHOTOS = "photos.json";
    public static final String EVENTS = "events.json";

    public static void addPhoto(JSONObject pic, Context c) {
        JSONArray photos = readPictures(c);
        photos.put(pic);
        storePhotos(photos, c);
    }

    private static void storePhotos(JSONArray photos, final Context c) {
        try {
            CharStreams.write(photos.toString(), new OutputSupplier<OutputStreamWriter>() {
                @Override
                public OutputStreamWriter getOutput() throws IOException {
                    return new OutputStreamWriter(c.openFileOutput(PHOTOS, Context.MODE_PRIVATE));
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static JSONArray readPictures(Context c){
        try {
            return new JSONArray(CharStreams.toString(new InputStreamReader(c.openFileInput(PHOTOS))));
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new JSONArray();
    }
}
