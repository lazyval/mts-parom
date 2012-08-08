package me.levelapp.parom.http;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import com.google.common.io.CharStreams;
import com.google.common.io.InputSupplier;
import model.Parom;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.util.zip.GZIPInputStream;

/**
 * User: anatoly
 * Date: 07.08.12
 * Time: 9:22
 */
public class HttpExecutor {
    private static final String TAG = "Parom-HTTP";
    private static final String TMP_IMG = "tmp-compress-image.png";
    private static final int DESIRED_SIZE  = 400000;

    public static JSONObject uploadFile(String uri, File fileToUpload) {

        File compressedFile = compressFile(fileToUpload);


        String respStr = null;
        try {
            HttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost(uri);
            if (!post.containsHeader("Accept-Encoding")) {
                post.addHeader(new BasicHeader("Accept-Encoding", "gzip"));
            }
            MultipartEntity entity = new MultipartEntity();
            entity.addPart("imagedata", new FileBody(compressedFile));
            entity.addPart("device_id", new StringBody(Parom.inst().getDeviceId()));
            post.setEntity(entity);

            HttpResponse response = client.execute(post);

            InputStream is = response.getEntity().getContent();
            if (response.containsHeader("Content-Encoding")){
                is = new GZIPInputStream(is);
            }

            final InputStream finalIs = is;
            InputSupplier<InputStreamReader> supplier = new InputSupplier<InputStreamReader>() {
                @Override
                public InputStreamReader getInput() throws IOException {
                    return  new InputStreamReader(new BufferedInputStream(finalIs));
                }
            };

            respStr = CharStreams.toString(supplier);
            return new JSONObject(respStr);

        } catch (IOException e) {
            Log.e(TAG, "", e);
        } catch (JSONException e) {
            Log.e(TAG, "not a json " + respStr, e);
        }
        return null;

    }

    private static File compressFile(File fileToUpload) {
        if (Parom.inst().isWifiAvailable()){
            return fileToUpload;
        }
        long time = System.currentTimeMillis();
        long fileLength = fileToUpload.length();
        BitmapFactory.Options origOptions = new BitmapFactory.Options();
        origOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(fileToUpload.getAbsolutePath(), origOptions);
        origOptions.inSampleSize = 1;
        if (fileLength > DESIRED_SIZE) {
            while (fileLength / origOptions.inSampleSize / origOptions.inSampleSize > DESIRED_SIZE) {
                origOptions.inSampleSize ++;
            }
        }
        BitmapFactory.Options compressOptions = new BitmapFactory.Options();
        compressOptions.inSampleSize = origOptions.inSampleSize;
        Bitmap compressed = BitmapFactory.decodeFile(fileToUpload.getAbsolutePath(), compressOptions);
        try {
            File ret =  Parom.inst().getFileStreamPath(TMP_IMG);
            OutputStream out = new FileOutputStream(ret);
            if (compressed.compress(Bitmap.CompressFormat.PNG, 100,out)){
                Log.d(TAG, "compressed in " + (System.currentTimeMillis()- time));
                Log.d(TAG, "image compressed ");
                return ret;
            }

        } catch (FileNotFoundException e) {
            Log.e(TAG, "FTW, file should always can be created", e);
        }
        return fileToUpload;

    }

}
