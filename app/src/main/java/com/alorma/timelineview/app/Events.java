package com.alorma.timelineview.app;

import android.support.annotation.NonNull;
import com.alorma.timeline.TimelineView;

class Events {
  private String name;
  private int type;
  private int alignment;

  Events(@NonNull String name) {
    this(name, TimelineView.TYPE_DEFAULT);
  }

  Events(@NonNull String name, int type) {
    this(name, type, TimelineView.ALIGNMENT_DEFAULT);
  }

  Events(@NonNull String name, int type, int alignment) {
    this.name = name;
    this.type = type;
    this.alignment = alignment;
  }

  public String getName() {
    return name;
  }

  public void setName(@NonNull String name) {
    this.name = name;
  }

  @TimelineView.TimelineType
  int getType() {
    return type;
  }

  public void setType(@TimelineView.TimelineType int type) {
    this.type = type;
  }

  @TimelineView.TimelineAlignment
  int getAlignment() {
    return alignment;
  }

  public void setAlignment(@TimelineView.TimelineAlignment int alignment) {
    this.alignment = alignment;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("Events{");
    sb.append("name='").append(name).append('\'');
    sb.append(", type=").append(type);
    sb.append(", alignment=").append(alignment);
    sb.append('}');
    return sb.toString();
  }
}