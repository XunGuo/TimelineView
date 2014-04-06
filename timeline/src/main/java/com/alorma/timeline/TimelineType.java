package com.alorma.timeline;

/**
 * Created by Bernat on 06/04/2014.
 */
public enum TimelineType {
    FIRST,
    NORMAL,
    LAST;

    static TimelineType fromId(int type) {
        switch (type) {
            case -1:
                return TimelineType.FIRST;
            case 0:
                return TimelineType.NORMAL;
            case 1:
                return TimelineType.LAST;
            default:
                throw new IllegalArgumentException();
        }
    }
}
