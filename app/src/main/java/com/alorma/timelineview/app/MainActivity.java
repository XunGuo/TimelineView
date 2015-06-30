package com.alorma.timelineview.app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.alorma.timeline.TimelineType;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayList<Evento> eventos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(android.R.id.list);

        eventos = new ArrayList<Evento>();

        Evento eventoFirst = new Evento("Primer evento", TimelineType.START);
        eventos.add(eventoFirst);

        for (int i = 0; i < 20; i++) {
            if (i % 2 == 0) {
                eventos.add(new Evento("Evento " + (i + 1), TimelineType.MIDDLE));
            } else {
                eventos.add(new Evento("Evento " + (i + 1)));
            }
        }

        Evento eventoLast = new Evento("Último evento", TimelineType.END);
        eventos.add(eventoLast);

        listView.setAdapter(new EventosAdapter(this, eventos));
    }
}
