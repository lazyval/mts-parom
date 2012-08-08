package me.levelapp.parom.ui.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.google.common.eventbus.Subscribe;
import com.google.common.io.CharStreams;
import com.google.common.io.InputSupplier;
import me.levelapp.parom.R;
import me.levelapp.parom.model.JSONFiles;
import me.levelapp.parom.model.events.PictureEvent;
import me.levelapp.parom.ui.PhotoActivity;
import me.levelapp.parom.ui.adapters.GalleryAdapter;
import org.json.JSONArray;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * User: anatoly
 * Date: 07.08.12
 * Time: 13:50
 */
public class PhotoFragment extends ListFragment {

    private GalleryAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View ret = inflater.inflate(R.layout.fragment_photos, container, false);
        ListView v = (ListView) ret.findViewById(android.R.id.list);
        mAdapter = new GalleryAdapter(getActivity());
        reQuery();
        v.setAdapter(mAdapter);


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
    public void galleryUpdated(PictureEvent e){
        reQuery();
    }

    private void reQuery() {

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
        mAdapter.setData(data);

    }
}
