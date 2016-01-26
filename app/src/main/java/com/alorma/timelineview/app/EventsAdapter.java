package com.alorma.timelineview.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.alorma.timeline.TimelineView;

import java.util.List;

public class EventsAdapter extends ArrayAdapter<Events> {
    private final LayoutInflater layoutInflater;

    public EventsAdapter(Context context, List<Events> objects) {
        super(context, 0, objects);
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = layoutInflater.inflate(R.layout.item_main, null);

        TextView text = (TextView) view.findViewById(R.id.textView);
        TimelineView timeline = (TimelineView) view.findViewById(R.id.timeline);

        Events events = getItem(position);

        text.setText(events.getName());
        timeline.setTimelineType(events.getType());
        timeline.setTimelineAlignment(events.getAlignment());

        return view;
    }
}
