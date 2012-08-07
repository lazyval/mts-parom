package me.levelapp.parom.http;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;
import me.levelapp.parom.R;
import org.json.JSONObject;

import java.io.File;

/**
 * User: anatoly
 * Date: 07.08.12
 * Time: 9:52
 */
public class UploadPictureTask extends AsyncTask<File, Void, JSONObject> {
    private final String mUri;
    private final Context mContext;


    public UploadPictureTask(Context c ) {
        mUri = c.getString(R.string.upload_uri);
        mContext = c;
    }

    @Override
    protected JSONObject doInBackground(File... params) {

        return HttpExecutor.uploadFile(mUri, params[0]);
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        Toast.makeText(mContext, jsonObject.toString(), Toast.LENGTH_LONG).show();
    }
}
