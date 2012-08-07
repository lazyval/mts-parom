package me.levelapp.parom.ui.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import me.levelapp.parom.R;

/**
 * User: anatoly
 * Date: 07.08.12
 * Time: 13:41
 */
public class AchievementsFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View ret = inflater.inflate(R.layout.fragment_achievements, container, false);
        return ret;
    }
}
