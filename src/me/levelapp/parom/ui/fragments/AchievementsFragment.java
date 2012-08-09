package me.levelapp.parom.ui.fragments;


import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;
import me.levelapp.parom.R;
import me.levelapp.parom.ui.adapters.AchievementsAdapter;

/**
 * User: anatoly
 * Date: 07.08.12
 * Time: 13:41
 */
public class AchievementsFragment extends ListFragment {
    private static String[] values;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View ret = inflater.inflate(R.layout.fragment_achievements, container, false),
                achievementsLayout = ret.findViewById(R.id.achievements_layout);

        makeValues();
        Log.v("main", "values maden");
        fillGrid(achievementsLayout);
        Log.v("main", "grid filled");
        fillHeader(achievementsLayout);
        Log.v("main", "header filled");

        return ret;
    }

    private void makeValues() {
        int length = 50;
        values = new String[length];
        for(int i=0; i<length; i++)
            values[i] = String.valueOf(i);
    }

    private void fillGrid(View parentView) {
        GridView gridview = (GridView) parentView.findViewById(R.id.achievements_grid);
        gridview.setAdapter(new AchievementsAdapter(parentView.getContext(), values));
    }

    private void fillHeader(View parentView) {
        TextView headerView = (TextView) parentView.findViewById(R.id.achievement_header);
        headerView.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_launcher, 0, 0);
    }
}
