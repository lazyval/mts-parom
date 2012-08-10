package me.levelapp.parom.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import me.levelapp.parom.R;
import me.levelapp.parom.http.UploadPictureTask;
import me.levelapp.parom.utils.BaseActivity;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import static android.os.Environment.DIRECTORY_PICTURES;
import static android.os.Environment.getExternalStoragePublicDirectory;

/**
 * User: anatoly
 * Date: 07.08.12
 * Time: 17:17
 */
public class PhotoActivity extends BaseActivity {
    public static final String EXTRA_PHOTO_OBJ = "photo.json";
    public static final String EXTRA_PHOTO_FILE = "file.path";
    private ImageView mContent;
    private String mFilePath;
    private JSONObject mObj;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        mContent = (ImageView) findViewById(R.id.img_content);
        Bundle extras = getIntent().getExtras();

        if (extras != null){
            if (extras.containsKey(EXTRA_PHOTO_OBJ)){
//                {'file':'/some/path/to/file', 'date':'7 august 19:00', 'name':'Бухаловка', 'tag':'Longue Bar'}]
                try {
                    mObj = new JSONObject(extras.getString(EXTRA_PHOTO_OBJ));
                    mFilePath = mObj.optString("file");
                    decodeFile(mFilePath);
                } catch (JSONException e) {
                    finish();
                }
            } else {
                mFilePath = extras.getString(EXTRA_PHOTO_FILE);
                decodeFile(mFilePath);
                new UploadPictureTask(this).execute(new File(mFilePath));
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private String decodeFile(String filePath) {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inSampleSize = 3;
        Bitmap b = BitmapFactory.decodeFile(filePath, opts);
        mContent .setImageBitmap(b);
        return filePath;
    }

    public void onShareButtonClick(View view) {
        final Intent intent = createShareIntent();
        startActivity(Intent.createChooser(intent, "В какой социалочке?"));
    }


    private Intent createShareIntent() {
        final Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        shareIntent.setType("image/*");

        // For a file in shared storage.  For data in private storage, use a ContentProvider.
        File picture = new File(
                getExternalStoragePublicDirectory(DIRECTORY_PICTURES),"foo.jpg"
        );
        shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(picture));
        return shareIntent;
    }




}
