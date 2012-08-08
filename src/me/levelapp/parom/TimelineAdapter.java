package me.levelapp.parom;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.URL;
import java.nio.charset.MalformedInputException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class TimelineAdapter extends BaseExpandableListAdapter {
    private static final String TAG = "TimelineAdapter";
    private static final ArrayList<Event> EMPTY_EVENTS_LIST = new ArrayList<Event>();
    private final List<Event> events;
//    private final Context context;

    public TimelineAdapter(final Context context) {
//        this.context = context;
        final String jsonUrl = context.getString(R.string.json_events_uri);
        events = readEventsFromJSON(jsonUrl);
        Log.i(TAG, events.size() + " parsed");
        Collections.sort(events);
    }


    private List<Event> readEventsFromJSON(final String URI) {
        // read from remote
        String json = "";
        InputStream in = null;
        try {
            in = new URL(URI).openStream();
            json = IOUtils.toString(in);
        } catch (final MalformedInputException e) {
            Log.wtf(TAG, "Wrong JSON events url " + URI);
        } catch (final IOException e) {
            // TODO: fallback to the latest successful download
            Log.e(TAG, "Cannot read JSON file from " + URI + " Exact problem: " + e.getMessage());
            return EMPTY_EVENTS_LIST;
        } finally {
            IOUtils.closeQuietly(in);
        }

        // parse
        final Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        final Type listOfEvents = new TypeToken<Collection<Event>>() {
        }.getType();
        final List<Event> parsedEvents = gson.fromJson(json, listOfEvents);
        return parsedEvents == null ? EMPTY_EVENTS_LIST : parsedEvents;
    }

    @Override
    final public int getGroupCount() {
        return events.size();
    }

    @Override
    final public int getChildrenCount(int i) {
        return 1;
    }

    @Override
    final public Object getGroup(int i) {
        return events.get(i).name;
    }

    @Override
    final public Object getChild(int groupPos, int childPos) {
        return events.get(groupPos).description;
    }

    @Override
    final public long getGroupId(int i) {
        return 0;
    }

    @Override
    final public long getChildId(int i, int i1) {
        return 0;
    }

    @Override
    final public boolean hasStableIds() {
        return false;
    }

    @Override
    final public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        return null;
    }

    @Override
    final public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        return null;
    }

    @Override
    final public boolean isChildSelectable(int i, int i1) {
        return false;
    }
}