package com.alorma.timelineview.app;

import com.alorma.timeline.TimelineAlignment;
import com.alorma.timeline.TimelineType;

public class Events {
    private String name;
    private TimelineType type;
    private TimelineAlignment alignment;

    public Events(String name) {
        this.name = name;
        this.type = TimelineType.LINE;
    }

    public Events(String name, TimelineType type) {
        this.name = name;
        this.type = type;
    }

    public Events(String name, TimelineType type, TimelineAlignment alignment) {
        this.name = name;
        this.type = type;
        this.alignment = alignment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TimelineType getType() {
        return type;
    }

    public void setType(TimelineType type) {
        this.type = type;
    }

    public TimelineAlignment getAlignment() {
        return alignment;
    }

    public void setAlignment(TimelineAlignment alignment) {
        this.alignment = alignment;
    }

    @Override
    public String toString() {
        return "Events{" +
                "name='" + name + '\'' +
                ", type=" + type +
                ", alignment=" + alignment +
                '}';
    }
}
