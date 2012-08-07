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
 * Time: 13:50
 */
public class TimelineFragment extends Fragment {
    @Override
    final public View onCreateView(final LayoutInflater inflater,final  ViewGroup container,final  Bundle savedInstanceState) {
        final View ret = inflater.inflate(R.layout.fragment_events, container, false);
        return ret;
    }
}
