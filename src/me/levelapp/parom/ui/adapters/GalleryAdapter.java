package me.levelapp.parom.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.common.base.Joiner;
import com.google.common.io.CharStreams;
import com.google.common.io.InputSupplier;
import me.levelapp.parom.R;
import me.levelapp.parom.model.FileNames;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;

/**
 * User: anatoly
 * Date: 07.08.12
 * Time: 14:28
 */
public class GalleryAdapter extends ArrayAdapter  implements InputSupplier<InputStreamReader>{

    private class GalleryItemHolder{
        ImageView img;
        TextView event;
        TextView place;

        public GalleryItemHolder(View convertView) {
            img = (ImageView) convertView.findViewById(R.id.img_content);
            event = (TextView) convertView.findViewById(R.id.label_event);
            place = (TextView) convertView.findViewById(R.id.label_place);

        }
    }
    private JSONArray mData ;
    private Context mContext;
    public GalleryAdapter(Context context) {
        super(context, 0);
        mContext = context;
        try {
            mData = new JSONArray(CharStreams.toString(this));
        } catch (Exception e) {
            mData = new JSONArray();
        }
    }

    @Override
    public InputStreamReader getInput() throws IOException {
        return new InputStreamReader(new BufferedInputStream(mContext.openFileInput(FileNames.PHOTOS)));
    }

    @Override
    public int getCount() {
        return mData.length();
    }

    @Override
    public Object getItem(int position) {
        return  mData.opt(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.view_gallery_item, parent, false);
            convertView.setTag(new GalleryItemHolder(convertView));
        }
        bindView(convertView, position);
        return convertView;
    }

    private void bindView(View convertView, int position) {
        GalleryItemHolder holder = (GalleryItemHolder) convertView.getTag();
        JSONObject obj = (JSONObject) getItem(position);
//        {'file':'/some/path/to/file', 'date':'7 august 19:00', 'name':'Бухаловка', 'tag':'Longue Bar'},
        holder.event.setText(Joiner.on(' ').join(obj.optString("date"),
                obj.optString("name")));
        holder.place.setText(obj.optString("tag"));

    }
}
