package me.levelapp.parom.utils;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Window;

/**
 * User: anatoly
 * Date: 08.08.12
 * Time: 14:36
 */
public class BaseActivity extends FragmentActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
    }
}
