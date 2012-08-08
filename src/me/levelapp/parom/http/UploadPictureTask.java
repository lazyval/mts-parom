package me.levelapp.parom.http;

import android.content.Context;
import android.os.AsyncTask;
import me.levelapp.parom.R;
import me.levelapp.parom.model.JSONFiles;
import me.levelapp.parom.model.Parom;
import me.levelapp.parom.model.events.PictureEvent;
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
    private final String mUri;
    private final Context mContext;
    private File mFIle;


    public UploadPictureTask(Context c) {
        mUri = c.getString(R.string.upload_uri);
        mContext = c;
    }

    @Override
    protected JSONObject doInBackground(File... params) {
        mFIle = params[0];
        return HttpExecutor.uploadFile(mUri, params[0]);
    }
    SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yy HH.mm");

    @Override
    protected void onPostExecute(JSONObject jsonObject) {

        //                {'file':'/some/path/to/file', 'date':'7 august 19:00', 'name':'Бухаловка', 'tag':'Longue Bar'}]

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
}
