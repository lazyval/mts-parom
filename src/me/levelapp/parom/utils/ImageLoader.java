package me.levelapp.parom.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import com.google.common.io.ByteStreams;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ImageLoader {
    public static final String TAG = "VK-CHAT-IMAGE-DOWNLOADER";
    MemoryCache memoryCache = new MemoryCache();
    FileCache fileCache;
    private Map<Displayable, String> imageViews = Collections.synchronizedMap(new WeakHashMap<Displayable, String>());

    ExecutorService executorService;

    public ImageLoader(Context context) {
        fileCache = new FileCache(context);
        executorService = Executors.newFixedThreadPool(3);
    }

    //    final int stub_id= R.drawable.stub;
    public void displayURI(String uri, Displayable imageView) {
        displayImage(uri, imageView, null);
    }

    public void displayFile(String filePath, Displayable imageView) {
        imageViews.put(imageView, filePath);
        Bitmap b = memoryCache.get(filePath);
        if (b != null) {
            imageView.display(b);
        } else {
            queueFile(filePath, imageView);
        }

    }

    private void queueFile(String filePath, Displayable imageView) {
        PhotoToLoad p = new PhotoToLoad(filePath, imageView, 400);
        executorService.execute(new FileLoader(p));
    }

    public void displayImage(String url, Displayable imageView, Integer desiredSize) {
        imageViews.put(imageView, url);
        Bitmap bitmap = memoryCache.get(url);
        if (bitmap != null)
            imageView.display(bitmap);
        else {

            queueUrl(url, imageView, desiredSize);
            imageView.display(null);

        }
    }

    public Bitmap getCachedBitmap(String url) {
        return memoryCache.get(url);
    }

    private void queueUrl(String url, Displayable imageView, Integer desiredSize) {
        PhotoToLoad p = new PhotoToLoad(url, imageView, desiredSize);
        executorService.execute(new PhotosLoader(p));
    }

    private Bitmap getBitmap(PhotoToLoad photoToLoad) {
        File f = fileCache.getFile(photoToLoad.url);

        //from SD cache
        Bitmap b = decodeFile(photoToLoad, f);
        if (b != null)
            return b;

        //from web
        try {
            Bitmap bitmap = null;
            URL imageUrl = new URL(photoToLoad.url);
            HttpURLConnection conn = (HttpURLConnection) imageUrl.openConnection();
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            conn.setInstanceFollowRedirects(true);
            InputStream is = conn.getInputStream();
            OutputStream os = new FileOutputStream(f);
//            Utils.CopyStream(is, os);
            ByteStreams.copy(is, os);
            os.close();
            bitmap = decodeFile(photoToLoad, f);
            return bitmap;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    //decodes image and scales it to reduce memory consumption
    private Bitmap decodeFile(PhotoToLoad p, File f) {
        try {
            //decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(f), null, o);

            //Find the correct scale value. It should be the power of 2.
            final int REQUIRED_SIZE = p.desiredSize == null ? 70 : p.desiredSize;
            Log.d(TAG, "rEQUIRED_SIZE " + REQUIRED_SIZE);
            int width_tmp = o.outWidth, height_tmp = o.outHeight;
            int scale = 1;
            while (true) {
                if (width_tmp / 2 < REQUIRED_SIZE || height_tmp / 2 < REQUIRED_SIZE)
                    break;
                width_tmp /= 2;
                height_tmp /= 2;
                scale *= 2;
            }

            Log.d(TAG, "sample size: " + scale);
            //decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
        } catch (FileNotFoundException e) {
        }
        return null;
    }

    public MemoryCache getMemoryCache() {
        return memoryCache;
    }

    //Task for the queue
    private class PhotoToLoad {
        public String url;
        public Displayable imageView;
        public Integer desiredSize;

        public PhotoToLoad(String u, Displayable i) {
            url = u;
            imageView = i;
        }

        public PhotoToLoad(String u, Displayable i, Integer desiredSize) {
            this(u, i);
            this.desiredSize = desiredSize;
        }
    }

    class PhotosLoader implements Runnable {
        PhotoToLoad photoToLoad;

        PhotosLoader(PhotoToLoad photoToLoad) {
            this.photoToLoad = photoToLoad;
        }

        @Override
        public void run() {
            if (imageViewReused(photoToLoad))
                return;
            Bitmap bmp = getBitmap(photoToLoad);
            memoryCache.put(photoToLoad.url, bmp);
            if (imageViewReused(photoToLoad))
                return;


            BitmapDisplayer bd = new BitmapDisplayer(bmp, photoToLoad);
            Activity a = (Activity) photoToLoad.imageView.getContext();
            a.runOnUiThread(bd);


        }
    }

    class FileLoader implements Runnable {
        PhotoToLoad photoToLoad;

        FileLoader(PhotoToLoad p) {
            this.photoToLoad = p;
        }

        @Override
        public void run() {
            if (imageViewReused(photoToLoad))
                return;
            BitmapFactory.Options opt = new BitmapFactory.Options();
            opt.inSampleSize = 6;//todo replace this magic
            Bitmap bmp = BitmapFactory.decodeFile(photoToLoad.url, opt);
            memoryCache.put(photoToLoad.url, bmp);
            if (imageViewReused(photoToLoad))
                return;


            BitmapDisplayer bd = new BitmapDisplayer(bmp, photoToLoad);
            Activity a = (Activity) photoToLoad.imageView.getContext();
            a.runOnUiThread(bd);

        }
    }

    boolean imageViewReused(PhotoToLoad photoToLoad) {
        String tag = imageViews.get(photoToLoad.imageView);
        return tag == null || !tag.equals(photoToLoad.url);
    }

    //Used to display bitmap in the UI thread
    class BitmapDisplayer implements Runnable {
        Bitmap bitmap;
        PhotoToLoad photoToLoad;

        public BitmapDisplayer(Bitmap b, PhotoToLoad p) {
            bitmap = b;
            photoToLoad = p;
        }

        public void run() {
            if (imageViewReused(photoToLoad))
                return;
            if (bitmap != null) {
                photoToLoad.imageView.display(bitmap);
//                Log.d(TAG, bitmap.getByteCount() + " ");
            } else {
//                photoToLoad.imageView.setImageResource(stub_id);
            }


        }
    }

    public void clearCache() {
        memoryCache.clear();
        fileCache.clear();
        Log.d(TAG, "Cache cleared");
    }

    public void clearMemoryCache() {
        memoryCache.clear();
        Log.d(TAG, "Memory Cache cleared");
    }


    public interface Displayable {
        void display(Bitmap b);
        Context getContext();
    }

}
