package me.levelapp.parom.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;
import me.levelapp.parom.R;
import me.levelapp.parom.utils.BaseActivity;

public class MainActivity extends BaseActivity {

    private static final int REQUEST_CAMERA_CAPTURE = 0;
    private static final int REQUEST_PICK_FROM_GALLERY = 1;
    private static final String STATE_CAM_PHOTO_URI = "image-uri";

    private Animation rotateWheel;
    private Uri mImageUri;
    private ImageView wheel;

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mImageUri = Uri.parse(savedInstanceState.getString(STATE_CAM_PHOTO_URI));
    }


    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        wheel = (ImageView)findViewById(R.id.wheel_view);
        rotateWheel = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate);
        turnWheelRotationOn();
    }

    final public void turnWheelRotationOn() {
        wheel.setAnimation(rotateWheel);
    }

    final public void turnWheelRotationOff() {
        wheel.clearAnimation();
    }

    public void requestGallery() {
        final Intent takePictureFromGalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(takePictureFromGalleryIntent, REQUEST_PICK_FROM_GALLERY);
    }

    public void requestPhoto() {
        final ContentValues values = new ContentValues();
        if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
            //камера может работать только с sd карточкой.
            // Есть хак http://stackoverflow.com/questions/5252193/trouble-writing-internal-memory-android
            // но лучше я оставлю все так
            mImageUri =
                    getContentResolver().
                            insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, mImageUri);
            startActivityForResult(takePictureIntent, REQUEST_CAMERA_CAPTURE);
        } else {
            //show message "no sd card"
            Toast.makeText(this, getString(R.string.mount_sdcard), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mImageUri != null) {
            outState.putString(STATE_CAM_PHOTO_URI, mImageUri.toString());
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_PICK_FROM_GALLERY: {
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContentResolver().
                            query(selectedImage, filePathColumn, null, null, null);
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String filePath = cursor.getString(columnIndex);
                    cursor.close();
                    //filePath
                    goPhoto(filePath);
                    break;
                }
                case REQUEST_CAMERA_CAPTURE: {
                    Uri selectedImage = mImageUri;
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContentResolver().
                            query(selectedImage, filePathColumn, null, null, null);
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String filePath = cursor.getString(columnIndex);
                    cursor.close();
                    goPhoto(filePath);
                    break;
                }
            }
        }
    }

    private void goPhoto(String filePath) {
        Intent i = new Intent(this, PhotoActivity.class);
        i.putExtra(PhotoActivity.EXTRA_PHOTO_FILE, filePath);
        startActivity(i);
    }


    public void makePhoto(View v){
        final String []items = getResources().getStringArray(R.array.dialog_photo);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pick a color");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                if (item == 0){
                    requestPhoto();
                } else {
                    requestGallery();
                }
            }
        });
         builder.create().show();
    }
}
