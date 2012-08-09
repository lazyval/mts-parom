package me.levelapp.parom.http;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import me.levelapp.parom.R;
import me.levelapp.parom.model.JSONFiles;
import me.levelapp.parom.model.Parom;
import me.levelapp.parom.model.events.PictureEvent;
import me.levelapp.parom.model.events.RotateWheelEvent;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * User: anatoly
 * Date: 07.08.12
 * Time: 9:52
 */
public class UploadPictureTask extends AsyncTask<File, Void, JSONObject> {
    private static final SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yy HH.mm");
    private static final String TAG = "UploadPictureTask";
    private final String mUri;
    private final Context mContext;
    private File mFIle;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Log.d(TAG, "sending a message: " + RotateWheelEvent.TURN_WHEEL_ON);
        Parom.bus().post(RotateWheelEvent.TURN_WHEEL_ON);
    }

    public UploadPictureTask(Context c) {
        mUri = c.getString(R.string.upload_uri);
        mContext = c;
    }

    @Override
    protected JSONObject doInBackground(File... params) {
        mFIle = params[0];
        return HttpExecutor.uploadFile(mUri, params[0]);
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {

        //                {'file':'/some/path/to/file', 'date':'7 august 19:00', 'name':'Бухаловка', 'tag':'Longue Bar'}]
        Log.d(TAG, "sending a message: " + RotateWheelEvent.TURN_WHEEL_OFF);
        Parom.bus().post(RotateWheelEvent.TURN_WHEEL_OFF);
        try {
            JSONObject obj = new JSONObject();
            obj.put("file", mFIle.getAbsolutePath());

            obj.put("date", formatter.format(new Date()));
            obj.put("name", "WHATEVER");
            if (new Random().nextInt() % 2 == 0) {
                obj.put("tag", "WHATEVER");
            }

            //TODO сервер должен возвращать такой объект

            JSONFiles.addPhoto(obj, mContext);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Parom.bus().post(new PictureEvent());
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        Log.d(TAG, "sending a message: " + RotateWheelEvent.TURN_WHEEL_OFF);
        Parom.bus().post(RotateWheelEvent.TURN_WHEEL_OFF);
    }
}
