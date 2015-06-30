package com.alorma.timeline;

/**
 * Created by a557114 on 30/06/2015.
 */
public enum LineStyle {
    LINEAR,
    DASHED;

    static LineStyle fromId(int type) {
        switch (type) {
            case -1:
                return LineStyle.DASHED;
            case 0:
                return LineStyle.LINEAR;
            default:
                return LineStyle.LINEAR;
        }
    }
}
