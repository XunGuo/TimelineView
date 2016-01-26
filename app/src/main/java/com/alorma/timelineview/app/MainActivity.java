package com.alorma.timelineview.app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import com.alorma.timeline.RoundTimelineView;
import com.alorma.timeline.TimelineView;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView list = (ListView) findViewById(R.id.list);

        ArrayList<Events> items = new ArrayList<>();
        items.add(new Events(getString(R.string.item_first), TimelineView.TYPE_START));
        for (int i = 0; i < 20; i++) {
            items.add(new Events(String.format(getString(R.string.item_default), i + 1),
                TimelineView.TYPE_MIDDLE));
        }
        items.add(new Events(getString(R.string.item_last), TimelineView.TYPE_END));
        list.setAdapter(new EventsAdapter(this, items));

        RoundTimelineView timeline3_align_top =
            (RoundTimelineView) findViewById(R.id.timeline3_align_top);
        timeline3_align_top.setLineMiddleSize(
            getResources().getDimensionPixelOffset(R.dimen.large_timeline_1));
        timeline3_align_top.setTimelineStyle(TimelineView.STYLE_LINEAR);

        RoundTimelineView timeline3_align_bottom =
            (RoundTimelineView) findViewById(R.id.timeline3_align_bottom);
        timeline3_align_bottom.setLineMiddleSize(
            getResources().getDimensionPixelOffset(R.dimen.large_timeline_2));
        timeline3_align_bottom.setTimelineStyle(TimelineView.STYLE_LINEAR);
    }
}
