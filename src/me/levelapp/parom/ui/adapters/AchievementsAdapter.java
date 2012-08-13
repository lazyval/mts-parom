package me.levelapp.parom.ui.adapters;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
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
    private final Context context;
    private final String[] values;

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

    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.view_achivements_item, parent, false);
        }
        final TextView tv = (TextView) convertView.findViewById(R.id.label_achivements);
        tv.setText(Html.fromHtml(values[position]));
        return tv;
    }
}
