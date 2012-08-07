package me.levelapp.parom.model;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.util.Log;
import com.google.common.io.ByteStreams;

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
    private static final String TAG = "MTS-PAROM";



    @Override
    public void onCreate() {
        super.onCreate();
        inst  = this;
        mConMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        copyDebugPhotos();
    }

    private void copyDebugPhotos() {
        try {
            InputStream in = getAssets().open(FileNames.PHOTOS);
            OutputStream out = openFileOutput(FileNames.PHOTOS, MODE_PRIVATE);
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

}
