package me.levelapp.parom.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import com.devsmart.android.ui.HorizontalListView;
import me.levelapp.parom.R;
import me.levelapp.parom.ui.adapters.AchievementsAdapter;

/**
 * User: anatoly
 * Date: 07.08.12
 * Time: 13:41
 */
public class AchievementsFragment extends ListFragment {

    private String[] values;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View ret = inflater.inflate(R.layout.fragment_achievements, container, false),
                achievementsLayout = ret.findViewById(R.id.achievements_layout);
        values = new String[]{
                constructInfo("", "Продолжай в том же духе и у тебя все получится!"),
                constructInfo("", "Вся жизнь кино и мы актеры в ней"),
                constructInfo("", "In vino veritas"),
                constructInfo("", "Должно быть ты действительно азартный чувак!"),
                constructInfo("", ""),
                constructInfo("", "")
        };


        fillGrid(achievementsLayout);

//        fillHeader(achievementsLayout);

        return ret;
    }

    private void fillGrid(View parentView) {
        final HorizontalListView hlist = (HorizontalListView) parentView.findViewById(R.id.achievements_grid);
        final TextView summary = (TextView) parentView.findViewById(R.id.achievement_summary);


        final String[] names = new String[]{"Новичок", "Кинолюбитель", "Заливала", "Игрок", "Гурман", "Колумб"};

        hlist.setAdapter(new AchievementsAdapter(parentView.getContext(), names));
        hlist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                summary.setText(values[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                summary.setText(values[0]); // set info for the first badge
            }
        });
    }

//    private void fillHeader(View parentView) {
//        TextView headerView = (TextView) parentView.findViewById(R.id.achievement_header);
//        headerView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_launcher, 0);
//    }


    private static String constructInfo(String header, String description) {
        return "" + header + " \n" + description + "";
    }
}
