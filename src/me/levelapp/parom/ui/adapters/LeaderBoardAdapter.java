package me.levelapp.parom.ui.adapters;

import android.content.Context;
import android.widget.ArrayAdapter;
import org.json.JSONArray;

/**
 * User: anatoly
 * Date: 10.08.12
 * Time: 18:34
 */
public class LeaderBoardAdapter extends ArrayAdapter {
    JSONArray mData;
    public LeaderBoardAdapter(Context context, JSONArray arr) {
        super(context, 0);
        mData = arr;
    }


}
