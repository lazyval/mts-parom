package me.levelapp.parom.ui;

import android.app.Activity;
import android.os.Bundle;
import me.levelapp.parom.R;

/**
 * User: anatoly
 * Date: 07.08.12
 * Time: 17:17
 */
public class PhotoActivity extends Activity {
    public static final String EXTRA_PHOTO_OBJ = "photo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
    }
}
