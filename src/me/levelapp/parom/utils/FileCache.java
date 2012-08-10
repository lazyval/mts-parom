package me.levelapp.parom.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class FileCache {
    
    private File cacheDir;
    
    public FileCache(Context context){
        //Find the dir to save cached images
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
            cacheDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
//            cacheDir=new File(android.os.Environment.getExternalStorageDirectory(),"LazyList");
        else
            cacheDir=context.getCacheDir();
        if(!cacheDir.exists())
            cacheDir.mkdirs();
    }
    
    public File getFile(String url){
        //I identify images by hashcode. Not a perfect solution, good for the demo.
//        String filename=String.valueOf(url.hashCode());
        //Another possible solution (thanks to grantland)
        String filename = null;
        try {
            filename = URLEncoder.encode(url, "UTF-8");
        } catch (UnsupportedEncodingException ignored) {}
        return new File(cacheDir, filename);
        
    }
    
    public void clear(){
        File[] files=cacheDir.listFiles();
        if(files==null)
            return;
        for(File f:files)
            f.delete();
    }

}