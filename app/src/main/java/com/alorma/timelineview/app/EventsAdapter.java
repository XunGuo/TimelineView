/*
package com.alorma.timelineview.app;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.alorma.timeline.TimelineView;
import java.util.List;

class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.ViewHolderItem> {

  private final LayoutInflater inflater;
  private final List<Event> events;

  EventsAdapter(LayoutInflater inflater, List<Event> events) {
    this.inflater = inflater;
    this.events = events;
  }

  @Override
  public ViewHolderItem onCreateViewHolder(ViewGroup parent, int viewType) {
    return new ViewHolderItem(inflater.inflate(R.layout.item_main, parent, false));
  }

  @Override
  public void onBindViewHolder(ViewHolderItem holder, int position) {
    Event event = events.get(position);

    holder.text.setText(event.getName());
    holder.timeline.setTimelineType(event.getType());
    holder.timeline.setTimelineAlignment(event.getAlignment());
  }

  @Override
  public int getItemCount() {
    return events.size();
  }

  static class ViewHolderItem extends RecyclerView.ViewHolder {
    TextView text;
    TimelineView timeline;

    ViewHolderItem(View itemView) {
      super(itemView);

      text = (TextView) itemView.findViewById(R.id.textView);
      timeline = (TimelineView) itemView.findViewById(R.id.timeline);
    }
  }
}
*/