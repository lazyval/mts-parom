package me.levelapp.parom.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.google.common.base.Joiner;
import me.levelapp.parom.R;
import me.levelapp.parom.model.Parom;
import me.levelapp.parom.utils.SmoothImageView;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * User: anatoly
 * Date: 07.08.12
 * Time: 14:28
 */
public class GalleryAdapter extends ArrayAdapter  {

    private class GalleryItemHolder {
        SmoothImageView img;
        TextView event;
        TextView place;

        public GalleryItemHolder(View convertView) {
            img = (SmoothImageView) convertView.findViewById(R.id.img_content);
            event = (TextView) convertView.findViewById(R.id.label_event);
            place = (TextView) convertView.findViewById(R.id.label_place);

        }
    }

    private JSONArray mData;
    private Context mContext;

    public GalleryAdapter(Context context) {
        super(context, 0);
        mContext = context;


    }

    public void setData(JSONArray arr){
        mData = arr;
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return mData.length();
    }

    @Override
    public Object getItem(int position) {
        return mData.opt(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
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
//        Bitmap b = Parom.cache().get(obj.optString("file"));
//        if (b == null) {
//            BitmapFactory.Options opt = new BitmapFactory.Options();
//            opt.inSampleSize = 3;
//            b = BitmapFactory.decodeFile(obj.optString("file"), opt);
//            Parom.cache().put(obj.optString("file"), b);
//        }
//        holder.img.setImageBitmap(b);
        holder.img.setImageBitmap(null);
        Parom. getImageLoader().displayFile(obj.optString("file"), holder.img);
    }
}
