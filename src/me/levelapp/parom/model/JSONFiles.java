package me.levelapp.parom.model;

import android.content.Context;
import com.google.common.io.CharStreams;
import com.google.common.io.OutputSupplier;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * User: anatoly
 * Date: 07.08.12
 * Time: 16:29
 */
public class JSONFiles
{
    public static final String PHOTOS = "photos.json";
    public static final String EVENTS = "events.json";

    private static final SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yy HH.mm");

    private static void addPhoto(JSONObject pic, Context c) {
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

    public static void storePicture(File f, Context c){
        try {
            JSONObject obj = new JSONObject();
            obj.put("file", f.getAbsolutePath());

            obj.put("date", formatter.format(new Date()));
            obj.put("name", "WHATEVER");
            if (new Random().nextInt() % 2 == 0) {
                obj.put("tag", "WHATEVER");
            }

            JSONFiles.addPhoto(obj, c);
        } catch (JSONException e) {
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
