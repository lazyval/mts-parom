package me.levelapp.parom.ui.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import me.levelapp.parom.R;
import me.levelapp.parom.ui.PhotoActivity;
import me.levelapp.parom.ui.adapters.GalleryAdapter;

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
}
