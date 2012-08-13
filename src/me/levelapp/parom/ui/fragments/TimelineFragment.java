package me.levelapp.parom.ui.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.google.common.io.CharStreams;
import com.google.common.io.InputSupplier;
import me.levelapp.parom.R;
import me.levelapp.parom.model.JSONFiles;
import me.levelapp.parom.ui.adapters.EventsAdapter;
import me.levelapp.parom.utils.TabBarManager;
import org.json.JSONArray;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * User: anatoly
 * Date: 07.08.12
 * Time: 13:50
 */
public class TimelineFragment extends Fragment
        implements TabBarManager.Actionable
{

    private static EventsAdapter mAdapter;

    @Override
    final public View onCreateView(final LayoutInflater inflater , final ViewGroup container, final Bundle savedInstanceState) {
        final View ret = inflater.inflate(R.layout.fragment_events, container, false);
        TextView header = (TextView) inflater.inflate(R.layout.view_events_header, null, false);
        TextView footer = (TextView) inflater.inflate(R.layout.view_footer_mts, null, false);
        final ExpandableListView v = (ExpandableListView) ret.findViewById(android.R.id.list);
        v.addHeaderView(header);
        v.addFooterView(footer);
        mAdapter = new EventsAdapter(getActivity());
        reQuery(EventType.ALL);
        v.setAdapter(mAdapter);


        return ret;
    }

    private void reQuery(EventType type) {
        if (type.equals(EventType.ALL)){
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
        } else {
            mAdapter.setData(new JSONArray());
        }


    }


    @Override
    public View getActionView(FragmentActivity mActivity) {
        Spinner s = new Spinner(mActivity);
        s.setBackgroundResource(R.drawable.bg_spinner);

        final DropDownGalleryAdapter adapter = new DropDownGalleryAdapter(mActivity);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);
        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                reQuery(adapter.getType(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return s;
    }

    public static enum EventType {
        ALL,MY,NO_MY
    }
    private class DropDownGalleryAdapter extends ArrayAdapter {

        public DropDownGalleryAdapter(Context context) {
            super(context, R.layout.dropdown_gallery_item);
        }

        @Override
        public int getCount() {
            return EventType.values().length;
        }

        @Override
        public Object getItem(int position) {
            return getName(position);
        }

        public EventType getType(int position) {
            return EventType.values()[position];
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.dropdown_gallery_item, parent, false);
            }
            TextView v = (TextView) convertView;
            v.setGravity(Gravity.CENTER_VERTICAL);
            v.setText(getName(position));
            return v;
        }

        private String getName(int position) {
            switch (position) {
                case 0: {
                    return getContext().getString(R.string.all_events);
                }
                case 1: {
                    return getContext().getString(R.string.my_events);
                }
                case 2: {
                    return getContext().getString(R.string.not_my);
                }
                default:
                    return null;
            }
        }
    }

}
