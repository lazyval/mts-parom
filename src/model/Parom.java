package model;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.util.Log;

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
