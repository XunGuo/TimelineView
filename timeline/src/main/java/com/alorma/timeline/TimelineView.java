/**
 * The MIT License (MIT)

 Copyright (c) [year] [fullname]

 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights
 to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 copies of the Software, and to permit persons to whom the Software is
 furnished to do so, subject to the following conditions:

 The above copyright notice and this permission notice shall be included in all
 copies or substantial portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 SOFTWARE.
 */

package com.alorma.timeline;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class TimelineView extends View {
    private int mLineColor = Color.GRAY;
    private float mLineWidth = 3;

    private int mCircleColor = Color.BLUE;
    private float mCircleRadius = 2f;

    private int mCircleFirstColor = Color.GREEN;
    private float mCircleFirstRadius = 2f;

    private int mCircleLastColor = Color.RED;
    private float mCircleLastRadius = 2f;

    private TimelineType type = TimelineType.NORMAL;

    private Paint linePaint, circlePaint, circleFirstPaint, circleLastPaint;
    private float startX, startY;
    private float endX, endY;
    private float centerX, centerY;

    public TimelineView(Context context) {
        super(context);
        init(context, null, 0);
    }

    public TimelineView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public TimelineView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
    }

    private void init(Context context, AttributeSet attrs, int defStyle) {
        isInEditMode();
        if (attrs != null) {
            final TypedArray a = context.obtainStyledAttributes(
                    attrs, R.styleable.TimelineView, defStyle, 0);

            if (a != null) {
                mLineColor = a.getColor(R.styleable.TimelineView_lineColor, mLineColor);

                mLineWidth = a.getDimension(R.styleable.TimelineView_lineWidth, mLineWidth);

                mCircleColor = a.getColor(R.styleable.TimelineView_circleColor, mCircleColor);

                mCircleRadius = a.getFloat(R.styleable.TimelineView_circleRadius, mCircleRadius);

                mCircleFirstColor = a.getColor(R.styleable.TimelineView_circleFirstColor, mCircleFirstColor);

                mCircleFirstRadius = a.getFloat(R.styleable.TimelineView_circleFirstRadius, mCircleFirstRadius);

                mCircleLastColor = a.getColor(R.styleable.TimelineView_circleLastColor, mCircleLastColor);

                mCircleLastRadius = a.getFloat(R.styleable.TimelineView_circleLastRadius, mCircleLastRadius);

                int type = a.getInt(R.styleable.TimelineView_timeline_type, 0);

                this.type = TimelineType.fromId(type);

                a.recycle();
            }

            linePaint = new Paint();
            linePaint.setFlags(Paint.ANTI_ALIAS_FLAG);
            linePaint.setColor(mLineColor);

            circlePaint = new Paint();
            circlePaint.setFlags(Paint.ANTI_ALIAS_FLAG);
            circlePaint.setColor(mCircleColor);
            circlePaint.setStyle(Paint.Style.FILL_AND_STROKE);
            circlePaint.setStrokeWidth(mCircleRadius);

            circleFirstPaint = new Paint();
            circleFirstPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
            circleFirstPaint.setColor(mCircleFirstColor);
            circleFirstPaint.setStyle(Paint.Style.FILL_AND_STROKE);
            circleFirstPaint.setStrokeWidth(mCircleFirstRadius);

            circleLastPaint = new Paint();
            circleLastPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
            circleLastPaint.setColor(mCircleLastColor);
            circleLastPaint.setStyle(Paint.Style.FILL_AND_STROKE);
            circleLastPaint.setStrokeWidth(mCircleLastRadius);
        }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();

        int contentWidth = getWidth() - paddingLeft - paddingRight;
        int contentHeight = getHeight() - paddingTop - paddingBottom;

        startX = contentWidth / 2 - mLineWidth / 2;
        endX = contentWidth / 2 + mLineWidth / 2;
        startY = paddingTop;
        endY = contentHeight;

        centerX = contentWidth / 2;
        centerY = contentHeight / 2;

        if (type == TimelineType.FIRST) {
            drawFirst(canvas);
        } else if (type == TimelineType.LAST) {
            drawLast(canvas);
        } else {
            drawNormal(canvas);
        }
    }

    private void drawFirst(Canvas canvas) {
        canvas.drawRect(startX, centerY, endX, endY, linePaint);
        canvas.drawCircle(centerX, centerY, mCircleFirstRadius, circleFirstPaint);
    }

    private void drawNormal(Canvas canvas) {
        canvas.drawRect(startX, startY, endX, endY, linePaint);
        canvas.drawCircle(centerX, centerY, mCircleRadius, circlePaint);
    }

    private void drawLast(Canvas canvas) {
        canvas.drawRect(startX, startY, endX, centerY, linePaint);
        canvas.drawCircle(centerX, centerY, mCircleLastRadius, circleLastPaint);
    }

    public TimelineType getType() {
        return type;
    }

    public void setType(TimelineType type) {
        this.type = type;
    }
}
