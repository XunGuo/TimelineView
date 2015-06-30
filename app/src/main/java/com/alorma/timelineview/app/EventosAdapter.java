package com.alorma.timelineview.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.alorma.timeline.TimelineAlignment;
import com.alorma.timeline.TimelineView;

import java.util.List;

/**
 * Created by Bernat on 06/04/2014.
 */
public class EventosAdapter extends ArrayAdapter<Evento> {
    private final LayoutInflater mInflater;

    public EventosAdapter(Context context, List<Evento> objects) {
        super(context, 0, objects);
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = mInflater.inflate(R.layout.row, null);

        TextView text = (TextView) v.findViewById(R.id.textView);
        TimelineView timeline = (TimelineView) v.findViewById(R.id.timeline);

        Evento evento = getItem(position);

        text.setText(evento.getName());
        timeline.setTimelineType(evento.getTipo());
        timeline.setTimelineAlignment(evento.getAlignment());

        return v;
    }
}
