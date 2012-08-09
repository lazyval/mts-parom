package me.levelapp.parom.ui.adapters;

import android.content.Context;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import me.levelapp.parom.R;

/**
 * Created with IntelliJ IDEA.
 * User: Irina
 * Date: 09.08.12
 * Time: 12:17
 * To change this template use File | Settings | File Templates.
 */
public class AchievementsAdapter extends BaseAdapter {
    private Context context;
    private String[] values;

    public AchievementsAdapter(Context c, String[] array) {
        context = c;
        values = array;
    }

    public int getCount() {
        return values.length;
    }

    public Object getItem(int arg0) {
        return null;
    }

    public long getItemId(int arg0) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup arg2) {
        TextView tv;
        if (convertView == null) {  // if it's not recycled, initialize some attributes
            tv = new TextView(context);
            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
            tv.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_launcher, 0, 0);
            tv.setGravity(Gravity.CENTER);
        } else {
            tv = (TextView) convertView;
        }

        tv.setText(values[position]);

        return tv;
    }
}
