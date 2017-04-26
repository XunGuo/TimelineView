package com.alorma.timelineview.app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import com.alorma.timeline.RoundTimelineView;
import com.alorma.timeline.TimelineView;
import com.bumptech.glide.Glide;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    buildSamples();
    buildList();
  }

  private void buildSamples() {
    RoundTimelineView timelineView = (RoundTimelineView) findViewById(R.id.timeline1);
    Glide.with(this).load(R.drawable.avatar).into(timelineView);

    RoundTimelineView timeline3_align_top =
        (RoundTimelineView) findViewById(R.id.timeline3_align_top);
    timeline3_align_top.setIndicatorSize(
        getResources().getDimensionPixelOffset(R.dimen.large_timeline_1));
    timeline3_align_top.setTimelineType(TimelineView.TYPE_HIDDEN);

    Glide.with(this).load(R.drawable.avatar).into(timeline3_align_top);

    RoundTimelineView timeline3_align_bottom =
        (RoundTimelineView) findViewById(R.id.timeline3_align_bottom);
    timeline3_align_bottom.setIndicatorSize(
        getResources().getDimensionPixelOffset(R.dimen.large_timeline_2));
    timeline3_align_bottom.setTimelineStyle(TimelineView.STYLE_LINEAR);
  }

  private void buildList() {
    RecyclerView list = (RecyclerView) findViewById(R.id.list);

    ArrayList<Event> items = new ArrayList<>();
    items.add(new Event(getString(R.string.item_first), TimelineView.TYPE_START));
    for (int i = 0; i < 20; i++) {
      items.add(new Event(String.format(getString(R.string.item_default), i + 1),
          TimelineView.TYPE_MIDDLE));
    }
    items.add(new Event(getString(R.string.item_last), TimelineView.TYPE_END));

    list.setLayoutManager(new LinearLayoutManager(this));

    list.setAdapter(new EventsAdapter(LayoutInflater.from(this), items));
  }
}
