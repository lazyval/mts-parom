package me.levelapp.parom.ui.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import com.google.common.io.CharStreams;
import com.google.common.io.InputSupplier;
import me.levelapp.parom.R;
import me.levelapp.parom.model.JSONFiles;
import me.levelapp.parom.ui.adapters.EventsAdapter;
import org.json.JSONArray;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * User: anatoly
 * Date: 07.08.12
 * Time: 13:50
 */
public class TimelineFragment extends Fragment {

    private static EventsAdapter mAdapter;

    @Override
    final public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        final View ret = inflater.inflate(R.layout.fragment_events, container, false);
        final ExpandableListView v = (ExpandableListView) ret.findViewById(android.R.id.list);
        mAdapter = new EventsAdapter(getActivity());
        reQuery();
        v.setAdapter(mAdapter);

        int width = getActivity().getWindowManager().getDefaultDisplay().getWidth();

        v.setIndicatorBounds(width - GetDipsFromPixel(35), width - GetDipsFromPixel(5));

        return ret;
    }

    private void reQuery() {
        JSONArray arr;
        try {
            arr = new JSONArray(CharStreams.toString(new InputSupplier<InputStreamReader>() {
                @Override
                public InputStreamReader getInput() throws IOException {
                    return new InputStreamReader(new BufferedInputStream(getActivity().openFileInput(JSONFiles.EVENTS)));
                }
            }));
        } catch (Exception e) {
            arr = new JSONArray();
        }
        mAdapter.setData(arr);
    }

    //Convert pixel to dip
    public int GetDipsFromPixel(float pixels) {
        // Get the screen's density scale
        final float scale = getResources().getDisplayMetrics().density;
        // Convert the dps to pixels, based on density scale
        return (int) (pixels * scale + 0.5f);
    }

}
