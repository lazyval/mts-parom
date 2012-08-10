package me.levelapp.parom.http;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import me.levelapp.parom.R;
import me.levelapp.parom.model.Parom;
import me.levelapp.parom.model.events.PictureEvent;
import me.levelapp.parom.model.events.RotateWheelEvent;
import org.json.JSONObject;

import java.io.File;
import java.util.HashSet;

/**
 * User: anatoly
 * Date: 07.08.12
 * Time: 9:52
 */
public class UploadPictureTask extends AsyncTask<File, Void, JSONObject> {

    private static HashSet<UploadPictureTask> allRunningTasks = new HashSet<UploadPictureTask>();

    public static void checkUploads(){
        if (allRunningTasks.size()>0){
            Parom.bus().post(RotateWheelEvent.TURN_WHEEL_ON);
        }
    }


    private static final String TAG = "UploadPictureTask";
    private final String mUri;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        allRunningTasks.add(this);

        Log.d(TAG, "sending a message: " + RotateWheelEvent.TURN_WHEEL_ON);

        Parom.bus().post(RotateWheelEvent.TURN_WHEEL_ON);
    }

    public UploadPictureTask(Context c) {
        mUri = c.getString(R.string.upload_uri);
    }

    @Override
    protected JSONObject doInBackground(File... params) {
        return HttpExecutor.uploadFile(mUri, params[0]);
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {

        //                {'file':'/some/path/to/file', 'date':'7 august 19:00', 'name':'Бухаловка', 'tag':'Longue Bar'}]

        Parom.bus().post(RotateWheelEvent.TURN_WHEEL_OFF);

        Parom.bus().post(new PictureEvent());
        allRunningTasks.remove(this);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        allRunningTasks.remove(this);
        Parom.bus().post(RotateWheelEvent.TURN_WHEEL_OFF);
    }
}
