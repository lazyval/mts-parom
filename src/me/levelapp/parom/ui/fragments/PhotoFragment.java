package me.levelapp.parom.ui.fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.ListFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.google.common.eventbus.Subscribe;
import com.google.common.io.CharStreams;
import com.google.common.io.InputSupplier;
import me.levelapp.parom.R;
import me.levelapp.parom.model.JSONFiles;
import me.levelapp.parom.model.events.PictureEvent;
import me.levelapp.parom.ui.PhotoActivity;
import me.levelapp.parom.ui.adapters.GalleryAdapter;
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
public class PhotoFragment extends ListFragment implements TabBarManager.Actionable {

    private GalleryAdapter mAdapter;
    private ListView mList;

    @Override
    public void onResume() {
        super.onResume();
        reQuery(GalleryType.MY);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View ret = inflater.inflate(R.layout.fragment_photos, container, false);
        mList = (ListView) ret.findViewById(android.R.id.list);
        TextView footer = (TextView) inflater.inflate(R.layout.view_footer_mts, null, false);
        mList.addFooterView(footer);
        mAdapter = new GalleryAdapter(getActivity());


        return ret;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Intent i = new Intent(getActivity(), PhotoActivity.class);
        i.putExtra(PhotoActivity.EXTRA_PHOTO_OBJ, mAdapter.getItem(position).toString());
        startActivity(i);
    }

    @Subscribe
    public void galleryUpdated(PictureEvent e) {
        reQuery(GalleryType.MY);
    }

    private void reQuery(GalleryType type) {
        switch (type) {
            case MY: {
                JSONArray data;
                try {
                    data = new JSONArray(CharStreams.toString(new InputSupplier<InputStreamReader>() {
                        @Override
                        public InputStreamReader getInput() throws IOException {
                            return new InputStreamReader(new BufferedInputStream(getActivity().openFileInput(JSONFiles.PHOTOS)));
                        }
                    }));
                } catch (Exception e) {
                    data = new JSONArray();
                }
                mAdapter = new GalleryAdapter(getActivity());
                mAdapter.setData(data, false);
                mList.setAdapter(mAdapter);
                break;
            }
            case GEOMETRIA: {
                JSONArray data;
                try {
                    data = new JSONArray(CharStreams.toString(new InputSupplier<InputStreamReader>() {
                        @Override
                        public InputStreamReader getInput() throws IOException {
                            return new InputStreamReader(new BufferedInputStream(getActivity().openFileInput("geometria.json")));
                        }
                    }));
                } catch (Exception e) {
                    data = new JSONArray();
                }
                mAdapter = new GalleryAdapter(getActivity());
                mAdapter.setData(data, true);
                mList.setAdapter(mAdapter);
                break;
            }
            case DANCE:{
                JSONArray data = new JSONArray();
                mAdapter = new GalleryAdapter(getActivity());
                mAdapter.setData(data, false);
                mList.setAdapter(mAdapter);
                break;
            }
        }


    }

    @Override
    public View getActionView(FragmentActivity activity) {
        Spinner s = new Spinner(activity);
        s.setBackgroundResource(R.drawable.bg_spinner);

        final DropDownGalleryAdapter adapter = new DropDownGalleryAdapter(activity);
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

    public static enum GalleryType {
        MY, GEOMETRIA, DANCE


    }

    private class DropDownGalleryAdapter extends ArrayAdapter {

        public DropDownGalleryAdapter(Context context) {
            super(context, R.layout.dropdown_gallery_item);
        }

        @Override
        public int getCount() {
            return GalleryType.values().length;
        }

        @Override
        public Object getItem(int position) {
            return getName(position);
        }

        public GalleryType getType(int position) {
            return GalleryType.values()[position];
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
                    return getContext().getString(R.string.my_photos);
                }
                case 1: {
                    return getContext().getString(R.string.geometria);
                }
                case 2: {
                    return getContext().getString(R.string.dance);
                }
                default:
                    return null;
            }
        }
    }
}
