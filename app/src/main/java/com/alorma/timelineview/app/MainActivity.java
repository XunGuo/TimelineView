package com.alorma.timelineview.app;

import android.os.Bundle;

import com.alorma.timeline.RoundTimelineView;
import com.alorma.timeline.TimelineView;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buildSamples();
        buildList();
    }

    private void buildSamples() {
        RoundTimelineView timelineView = findViewById(R.id.timeline1);
        Glide.with(this).load(R.drawable.avatar).into(timelineView);

        RoundTimelineView timeline3_align_top = findViewById(R.id.timeline3_align_top);
        timeline3_align_top.setIndicatorSize(getResources().getDimensionPixelOffset(R.dimen.large_timeline_1));
        timeline3_align_top.setTimelineType(TimelineView.TYPE_HIDDEN);

        Glide.with(this).load(R.drawable.avatar).into(timeline3_align_top);

        RoundTimelineView timeline3_align_bottom = findViewById(R.id.timeline3_align_bottom);
        timeline3_align_bottom.setIndicatorSize(getResources().getDimensionPixelOffset(R.dimen.large_timeline_2));
        timeline3_align_bottom.setTimelineStyle(TimelineView.STYLE_LINEAR);
    }

    private void buildList() {
        RecyclerView list = findViewById(R.id.list);

        List<Event> items = new ArrayList<>();

        items.add(new Event(getString(R.string.item_first), TimelineView.TYPE_START));
        for (int i = 0; i < 20; i++) {
            items.add(new Event(String.format(getString(R.string.item_default), i + 1),
                    TimelineView.TYPE_MIDDLE));
        }
        items.add(new Event(getString(R.string.item_last), TimelineView.TYPE_END));

        list.setLayoutManager(new LinearLayoutManager(this));

        EventsAdapter adapter = new EventsAdapter();
        list.setAdapter(adapter);
        adapter.submitList(items);
    }
}
