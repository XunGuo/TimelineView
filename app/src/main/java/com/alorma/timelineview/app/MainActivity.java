package com.alorma.timelineview.app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.alorma.timeline.LineStyle;
import com.alorma.timeline.RoundTimelineView;
import com.alorma.timeline.TimelineType;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayList<Evento> eventos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.list);

        eventos = new ArrayList<>();

        Evento eventoFirst = new Evento("Primer evento", TimelineType.START);
        eventos.add(eventoFirst);

        for (int i = 0; i < 20; i++) {
            Evento evento = new Evento("Evento " + (i + 1));
            evento.setTipo(TimelineType.MIDDLE);
            eventos.add(evento);
        }

        Evento eventoLast = new Evento("Último evento", TimelineType.END);
        eventos.add(eventoLast);

        listView.setAdapter(new EventosAdapter(this, eventos));


        RoundTimelineView timeline3_align_top = (RoundTimelineView) findViewById(R.id.timeline3_align_top);
        timeline3_align_top.setMiddleSize(getResources().getDimensionPixelOffset(R.dimen.large_timeline_1));
        timeline3_align_top.setLineStyle(LineStyle.LINEAR);

        RoundTimelineView timeline3_align_bottom = (RoundTimelineView) findViewById(R.id.timeline3_align_bottom);
        timeline3_align_bottom.setMiddleSize(getResources().getDimensionPixelOffset(R.dimen.large_timeline_2));
        timeline3_align_bottom.setLineStyle(LineStyle.LINEAR);
    }
}
