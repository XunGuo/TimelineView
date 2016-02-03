package com.alorma.timeline;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;

public class RoundTimelineView extends TimelineView {
    public RoundTimelineView(Context context) {
        this(context, null);
    }

    public RoundTimelineView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundTimelineView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public RoundTimelineView(Context context, AttributeSet attrs, int defStyleAttr,
        int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void drawIndicator(Canvas canvas, Paint paintStart, float centerX, float centerY,
        float size) {
        drawCircle(canvas, centerX, centerY, size, paintStart);
    }

    @Override
    protected void drawInternal(Canvas canvas, Paint paintInternal, float centerX, float centerY,
        float size) {
        drawCircle(canvas, centerX, centerY, size, paintInternal);
    }

    private void drawCircle(Canvas canvas, float centerX, float centerY, float radius,
        Paint paint) {
        if (canvas != null) {
            canvas.drawCircle(centerX, centerY, radius, paint);
        }
    }
}
