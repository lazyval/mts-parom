package me.levelapp.parom.model;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.util.Log;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.common.io.ByteStreams;
import me.levelapp.parom.model.events.BaseEvent;
import me.levelapp.parom.utils.ImageLoader;
import me.levelapp.parom.utils.MemoryCache;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * User: anatoly
 * Date: 07.08.12
 * Time: 9:30
 */
public class Parom extends Application {
    private  static Parom inst;
    private ConnectivityManager mConMgr;
    private static final String TAG = "MTS-PAROM-EVENT";

    private ImageLoader mImageLoader;
    private EventBus bus ;
    private static SharedPreferences prefs;

    public static SharedPreferences getPrefs() {
        return prefs;
    }

    public static final String MARIA = "maria";
    public static final String ANASTASIA = "anastasiya";

    public static String getParomName(){
        return inst().getSharedPreferences(TAG, MODE_PRIVATE)
                .getString("parom-name", null);
    }

    public static void storeParomName(String paromName){
        SharedPreferences.Editor e = inst().getSharedPreferences(TAG, MODE_PRIVATE).edit();

        e.putString("parom-name", paromName);
        e.commit();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        inst  = this;
        mConMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        copyDebugPhotos();
        bus = new EventBus();
        bus.register(this);
        mImageLoader = new ImageLoader(this);
        prefs = getSharedPreferences(TAG, MODE_PRIVATE);
    }


    @Subscribe public void logEvent(BaseEvent e){
        Log.d(TAG, e.toString());
    }

    public static MemoryCache cache(){
        return inst().mImageLoader.getMemoryCache();
    }
    public static EventBus bus(){
        return inst().bus;
    }
    private void copyDebugPhotos() {
        try {
            InputStream in = getAssets().open(JSONFiles.EVENTS);
            OutputStream out = openFileOutput(JSONFiles.EVENTS, MODE_PRIVATE);
            ByteStreams.copy(in, out);
            in.close();
            out.flush();
            out.close();
        } catch (IOException e) {
            throw new RuntimeException("FTW? :O");
        }
    }

    public static Parom inst(){
        return inst;
    }

    public boolean isNetworkAvailable() {

        NetworkInfo activeNetwork = mConMgr.getActiveNetworkInfo();
        if (activeNetwork == null) {
            return false;
        }
        if (activeNetwork.getState() == NetworkInfo.State.CONNECTED) {
            return true;
        } else if (activeNetwork.getState() == NetworkInfo.State.DISCONNECTED) {
            return false;
        }
        Log.d(TAG, "WIFI " +
                mConMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState());

        //we don't know the state, so may be let's try
        return true;
    }

    public boolean isWifiAvailable() {
        return  mConMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED;
    }

    public String getDeviceId(){
        AccountManager am = AccountManager.get(this);
        Account[] googleAccounts = am.getAccountsByType("com.google");
        if (googleAccounts.length > 0){
            return googleAccounts[0].name;
        } else {
            TelephonyManager tManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
            return tManager.getDeviceId();
        }
    }

    public static ImageLoader getImageLoader() {
        return inst().mImageLoader;
    }
}
