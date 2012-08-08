package me.levelapp.parom.ui.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import me.levelapp.parom.R;
import me.levelapp.parom.TimelineAdapter;

/**
 * User: anatoly
 * Date: 07.08.12
 * Time: 13:50
 */
public class TimelineFragment extends Fragment {

    private static TimelineAdapter adapter;

    @Override
    final public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        final View ret = inflater.inflate(R.layout.fragment_events, container, false);
        final ExpandableListView v = (ExpandableListView) ret.findViewById(android.R.id.list);
        adapter = new TimelineAdapter(getActivity());
        v.setAdapter(adapter);
        return ret;
    }
}
