package me.levelapp.parom;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.levelapp.parom.http.DownloadURLToStringTask;

import java.lang.reflect.Type;
import java.util.Arrays;

public class TimelineAdapter extends BaseExpandableListAdapter {
    private static final String TAG = "TimelineAdapter";
    private static final Event[] EMPTY_EVENTS_LIST = new Event[]{};
    private final Event[] events;

    private final Context context;

    public TimelineAdapter(final Context context) {
        this.context = context;
        final String jsonUrl = context.getString(R.string.json_events_uri);
        final String json = new DownloadURLToStringTask().doInBackground(jsonUrl);
        events = parseFromJSON(json);
        Log.i(TAG, events.length + " parsed");
        Arrays.sort(events);
    }


    private Event[] parseFromJSON(final String json) {
        final Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        final Type listOfEvents = new TypeToken<Event[]>() {}.getType();
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
        Log.i(TAG, "Get group");
        return events[i].name;
    }

    @Override
    final public Object getChild(int groupPos, int childPos) {
        Log.i(TAG, "Get child");
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
    final public View getGroupView(int i, boolean isExpanded, View view, ViewGroup viewGroup) {
        Log.i(TAG, "Get group view");
        Log.i(TAG, "Group view is "+view);
        if(view == null)
            view = new TextView(context);
        return view;
    }

    @Override
    final public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView , ViewGroup parent) {
        Log.i(TAG, "Get child group view");
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.view_event_item, parent, false);

        }
        return convertView;
    }

    @Override
    final public boolean isChildSelectable(int i, int i1) {
        return false;
    }
}