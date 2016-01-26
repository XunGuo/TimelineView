package com.alorma.timeline;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;

public class SquareTimelineView extends TimelineView {
    private RectF rectF;

    public SquareTimelineView(Context context) {
        this(context, null);
    }

    public SquareTimelineView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SquareTimelineView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        rectF = new RectF();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SquareTimelineView(Context context, AttributeSet attrs, int defStyleAttr,
        int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    @Override public void drawStart(Canvas canvas, Paint paintStart, float centerX, float centerY,
        float startSize) {
        drawSquare(canvas, centerX, centerY, startSize, paintStart);
    }

    @Override public void drawMiddle(Canvas canvas, Paint paintMiddle, float centerX, float centerY,
        float middleSize) {
        drawSquare(canvas, centerX, centerY, middleSize, paintMiddle);
    }

    @Override public void drawEnd(Canvas canvas, Paint paintEnd, float centerX, float centerY,
        float endSize) {
        drawSquare(canvas, centerX, centerY, endSize, paintEnd);
    }

    @Override protected void drawInternalStart(Canvas canvas, Paint paintInternal, float centerX,
        float centerY, float size) {
        drawSquare(canvas, centerX, centerY, size, paintInternal);
    }

    @Override protected void drawInternalMiddle(Canvas canvas, Paint paintInternal, float centerX,
        float centerY, float size) {
        drawSquare(canvas, centerX, centerY, size, paintInternal);
    }

    @Override
    protected void drawInternalEnd(Canvas canvas, Paint paintInternal, float centerX, float centerY,
        float size) {
        drawSquare(canvas, centerX, centerY, size, paintInternal);
    }

    private void drawSquare(Canvas canvas, float centerX, float centerY, float size, Paint paint) {
        if (canvas != null) {
            rectF.left = centerX - size;
            rectF.top = centerY - size;
            rectF.right = centerX + size;
            rectF.bottom = centerY + size;
            canvas.drawRect(rectF.left, rectF.top, rectF.right, rectF.bottom, paint);
        }
    }
}
