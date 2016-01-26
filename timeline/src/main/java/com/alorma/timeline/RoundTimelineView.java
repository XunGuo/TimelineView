package com.alorma.timeline;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;

public class RoundTimelineView extends TimelineView {
    public RoundTimelineView(Context context) {
        super(context);
    }

    public RoundTimelineView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RoundTimelineView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public RoundTimelineView(Context context, AttributeSet attrs, int defStyleAttr,
        int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override public void drawStart(Canvas canvas, Paint paintStart, float centerX, float centerY,
        float startSize) {
        drawCircle(canvas, centerX, centerY, startSize, paintStart);
    }

    @Override public void drawMiddle(Canvas canvas, Paint paintMiddle, float centerX, float centerY,
        float middleSize) {
        drawCircle(canvas, centerX, centerY, middleSize, paintMiddle);
    }

    @Override public void drawEnd(Canvas canvas, Paint paintEnd, float centerX, float centerY,
        float endSize) {
        drawCircle(canvas, centerX, centerY, endSize, paintEnd);
    }

    @Override protected void drawInternalStart(Canvas canvas, Paint paintInternal, float centerX,
        float centerY, float radius) {
        drawCircle(canvas, centerX, centerY, radius, paintInternal);
    }

    @Override protected void drawInternalMiddle(Canvas canvas, Paint paintInternal, float centerX,
        float centerY, float radius) {
        drawCircle(canvas, centerX, centerY, radius, paintInternal);
    }

    @Override
    protected void drawInternalEnd(Canvas canvas, Paint paintInternal, float centerX, float centerY,
        float radius) {
        drawCircle(canvas, centerX, centerY, radius, paintInternal);
    }

    private void drawCircle(Canvas canvas, float centerX, float centerY, float radius,
        Paint paint) {
        if (canvas != null) {
            canvas.drawCircle(centerX, centerY, radius, paint);
        }
    }
}
