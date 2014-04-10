package com.alorma.timelineview.app;

import com.alorma.timeline.TimelineType;

/**
 * Created by Bernat on 06/04/2014.
 */
public class Evento {

    private String name;
    private TimelineType tipo;

    public Evento(String name) {
        this.name = name;
        this.tipo = TimelineType.LINE;
    }

    public Evento(String name, TimelineType tipo) {
        this.name = name;
        this.tipo = tipo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TimelineType getTipo() {
        return tipo;
    }

    public void setTipo(TimelineType tipo) {
        this.tipo = tipo;
    }
}
