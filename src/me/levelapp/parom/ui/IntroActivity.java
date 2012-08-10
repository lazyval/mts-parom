package me.levelapp.parom.ui;

import android.os.Bundle;
import android.view.View;
import me.levelapp.parom.R;
import me.levelapp.parom.model.Parom;
import me.levelapp.parom.utils.BaseActivity;

/**
 * User: anatoly
 * Date: 10.08.12
 * Time: 14:36
 */
public class IntroActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
    }


    public void selectParom(View v ){
        if (v.getId() == R.id.btn_maria){
            Parom.storeParomName(Parom.MARIA);
        } else {
            Parom.storeParomName(Parom.ANASTASIA);
        }
        finish();
    }
}
