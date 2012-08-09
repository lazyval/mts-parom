package me.levelapp.parom.ui.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.levelapp.parom.Event;
import me.levelapp.parom.R;
import me.levelapp.parom.http.DownloadURLToStringTask;

import java.lang.reflect.Type;
import java.util.Arrays;

public class TimelineAdapter extends BaseExpandableListAdapter {
    private static final String TAG = "TimelineAdapter";
    private static final Event[] EMPTY_EVENTS_LIST = new Event[]{};
    private final Event[] events;
    private final Context context;
    private final LayoutInflater inflater;

    public TimelineAdapter(final Context context) {
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final String jsonUrl = context.getString(R.string.json_events_uri);
        final String json = new DownloadURLToStringTask().doInBackground(jsonUrl);
        events = parseFromJSON(json);
        Log.i(TAG, events.length + " parsed");
        Arrays.sort(events);
    }


    private Event[] parseFromJSON(final String json) {
        final Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        final Type listOfEvents = new TypeToken<Event[]>() {
        }.getType();
        // could throw com.google.gson.JsonSyntaxException
        final Event[] parsedEvents = gson.fromJson(json, listOfEvents);
        return parsedEvents == null ? EMPTY_EVENTS_LIST : parsedEvents;
    }

    @Override
    final public int getGroupCount() {
        return events.length;
    }

    @Override
    final public int getChildrenCount(int i) {
        return 1;
    }

    @Override
    final public Object getGroup(int i) {
        return events[i].name;
    }

    @Override
    final public Object getChild(int groupPos, int childPos) {
        return events[groupPos].description;
    }

    @Override
    final public long getGroupId(int i) {
        return 0;
    }

    @Override
    final public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    final public boolean hasStableIds() {
        return true;
    }

    @Override
    final public View getGroupView(int groupPosition, boolean isExpanded, View view, ViewGroup parent) {
        if (view == null) {
            view = inflater.inflate(R.layout.view_event_item_header, parent, false);
            view.setTag(groupPosition);
            ((TextView) view.findViewById(R.id.event_name)).setText(events[groupPosition].name);
        }
        return view;
    }

    @Override
    final public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.view_event_item_child, parent, false);
            convertView.setTag(groupPosition);

            ((TextView) convertView.findViewById(R.id.event_location)).setText(events[groupPosition].location);
            ((TextView) convertView.findViewById(R.id.event_description)).setText(events[groupPosition].description);
            ((ImageView) convertView.findViewById(R.id.event_image)).setImageBitmap(Bitmap.createBitmap(10, 20, null));
        }
        return convertView;
    }

//    private void bindChildView(int position) {
//
//    }


    @Override
    final public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}