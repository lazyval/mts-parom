package me.levelapp.parom.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import me.levelapp.parom.R;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * User: anatoly
 * Date: 29.07.12
 * Time: 23:04
 */
public class EventsAdapter extends BaseExpandableListAdapter {
    private class EventHolder {
        public ImageView img;
        public TextView event;
        public TextView placeName;
        public TextView placeAddress;
        public TextView timeRemaining;
        int position;
        View root;


        public EventHolder(View root) {
            this.root = root;
            img = (ImageView) root.findViewById(R.id.img);
            event = (TextView) root.findViewById(R.id.label_event);
            placeName = (TextView) root.findViewById(R.id.label_place);
            placeAddress = (TextView) root.findViewById(R.id.label_address);
            timeRemaining = (TextView) root.findViewById(R.id.label_time_remaining);
            root.setTag(this);
        }
    }

    private class EventDescHolder implements View.OnClickListener {
        public TextView desc;
        public Button btnGo;
        View root;
        int position;

        private EventDescHolder(View root) {
            this.root = root;
            desc = (TextView) root.findViewById(R.id.label_event_description);
            btnGo = (Button) root.findViewById(R.id.btn_go);
            btnGo.setOnClickListener(this);
            root.setTag(this);
        }

        @Override
        public void onClick(View v) {
            Button b = (Button) v;
            JSONObject obj = mData.optJSONObject(position);
            try {
                if (obj.optBoolean("go", false)) {
                    obj.put("go", false);
                } else {
                    obj.put("go", true);
                }
            } catch (JSONException ignored) {
            }
            notifyDataSetChanged();
        }
    }

    private final Context mContext;
    JSONArray mData;


    public EventsAdapter(Context c) {
        mContext = c;
    }


    public void setData(JSONArray arr) {
        mData = arr;
        notifyDataSetChanged();
    }

    @Override
    public int getGroupCount() {
        return mData.length();
    }

    @Override
    public int getChildrenCount(int i) {

        return 1;
    }

    @Override
    public Object getGroup(int i) {
        return mData.opt(i);  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Object getChild(int i, int i1) {
        return mData.opt(i);  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i * 1000 + i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View convertView, ViewGroup viewGroup) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.view_event, viewGroup, false);
            new EventHolder(convertView);
        }
        JSONObject obj = mData.optJSONObject(i);
        EventHolder h = (EventHolder) convertView.getTag();
        h.event.setText( obj.optString("name"));
        h.placeName.setText(obj.optString("place"));
        h.placeAddress.setText(obj.optString("address"));
        h.timeRemaining.setText(" Осталось 5 минут");

//        "name": "Бухаловка",
//                "date": "2012-06-06",
//                "place": "Lounge Bar",
//                "description": "Выпей сколько сможешь!",
//                "images":[
//        ""
//        ]
//        boolean go = obj.optBoolean("go", false);
//        if (go) {
//            h.root.setBackgroundColor(Color.GREEN);
//        } else {
//            h.root.setBackgroundColor(Color.TRANSPARENT);
//        }



        h.position = i;
        return convertView;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View convertView, ViewGroup viewGroup) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.view_event_desc, viewGroup, false);
            new EventDescHolder(convertView);
        }
        JSONObject obj = mData.optJSONObject(i);
        EventDescHolder holder = (EventDescHolder) convertView.getTag();
        holder.position = i;

        holder.desc.setText(obj.optString("description"));
        boolean go = obj.optBoolean("go", false);
//        if (go) {
//            holder.btnGo.setText(mContext.getString(R.string.do_not_go));
//            holder.root.setBackgroundColor(Color.GREEN);
//        } else {
//            holder.btnGo.setText(mContext.getString(R.string.go));
//            holder.root.setBackgroundColor(Color.TRANSPARENT);
//        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }
}
