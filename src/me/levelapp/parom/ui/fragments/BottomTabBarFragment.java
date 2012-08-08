package me.levelapp.parom.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import me.levelapp.parom.R;
import me.levelapp.parom.utils.TabBarManager;

/**
 * User: anatoly
 * Date: 07.06.12
 * Time: 22:54
 */
public class BottomTabBarFragment extends Fragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_tabbar_bottom, null, false);
        RadioGroup grp = (RadioGroup) root.findViewById(R.id.tabbar_bottom);
        TabBarManager barManager = new TabBarManager(getActivity(), grp, R.id.tab_content);


        barManager.add(R.id.tab_events, TimelineFragment.class);
        barManager.add(R.id.tab_photo, PhotoFragment.class);
        barManager.add(R.id.tab_achieve, AchievementsFragment.class);


        grp.check(R.id.tab_events);


        return root;

    }

}


