/**
 * The MIT License (MIT)
 * <p/>
 * Copyright (c) [year] [fullname]
 * <p/>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p/>
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * <p/>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.alorma.timeline;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.ImageView;

public abstract class TimelineView extends ImageView {

    private int mLineColor = Color.GRAY;
    private float mLineWidth = 3f;

    private int mColorMiddle = -1;
    private float mMiddleSize = 2f;

    private int mFirstColor = -1;
    private float mStartSize = 2f;

    private int mLastColor = -1;
    private float mEndSize = 2f;

    private TimelineType timelineType = TimelineType.MIDDLE;
    private TimelineAlignment timelineAlignment = TimelineAlignment.MIDDLE;

    private Paint linePaint, middlePaint, firstPaint, lastPaint;
    private RectF itemRect;
    private Rect viewRect;

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

        mLineColor = AttributesUtils.colorPrimary(context, Color.parseColor("#ff0000"));
        mColorMiddle = mFirstColor = mLastColor = AttributesUtils.colorAccent(context, Color.parseColor("#00ff00"));
        mLineWidth = getContext().getResources().getDimensionPixelOffset(R.dimen.timeline_lineWidth);
        mMiddleSize = mStartSize = mEndSize = getContext().getResources().getDimensionPixelOffset(R.dimen.timeline_itemSize);

        if (attrs != null) {
            final TypedArray a = context.getTheme().obtainStyledAttributes(
                    attrs, R.styleable.TimelineView, defStyle, 0);

            if (a != null) {
                mLineColor = a.getColor(R.styleable.TimelineView_lineColor, mLineColor);

                mLineWidth = a.getDimension(R.styleable.TimelineView_lineWidth, mLineWidth);

                mColorMiddle = a.getColor(R.styleable.TimelineView_middleColor, mColorMiddle);

                mMiddleSize = a.getFloat(R.styleable.TimelineView_middleSize, mMiddleSize);

                mFirstColor = a.getColor(R.styleable.TimelineView_firstColor, mFirstColor);

                mStartSize = a.getFloat(R.styleable.TimelineView_firstSize, mStartSize);

                mLastColor = a.getColor(R.styleable.TimelineView_lastColor, mLastColor);

                mEndSize = a.getFloat(R.styleable.TimelineView_lastSize, mEndSize);

                int type = a.getInt(R.styleable.TimelineView_timeline_type, 0);

                this.timelineType = TimelineType.fromId(type);

                int alignment = a.getInt(R.styleable.TimelineView_timeline_alignment, 0);

                this.timelineAlignment = TimelineAlignment.fromId(alignment);

                a.recycle();
            }
        }
        if (mColorMiddle == -1) {
            mColorMiddle = mLineColor;
        }

        if (mFirstColor == -1) {
            mFirstColor = mLineColor;
        }

        if (mLastColor == -1) {
            mLastColor = mLineColor;
        }

        linePaint = new Paint();
        linePaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        linePaint.setColor(mLineColor);

        middlePaint = new Paint();
        middlePaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        middlePaint.setColor(mColorMiddle);
        middlePaint.setStyle(Paint.Style.FILL);

        firstPaint = new Paint();
        firstPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        firstPaint.setColor(mFirstColor);
        firstPaint.setStyle(Paint.Style.FILL);

        lastPaint = new Paint();
        lastPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        lastPaint.setColor(mLastColor);
        lastPaint.setStyle(Paint.Style.FILL);

        itemRect = new RectF();

        viewRect = new Rect();
    }


    @Override
    protected void onDraw(Canvas canvas) {

        canvas.getClipBounds(viewRect);

        float startX = viewRect.width() / 2 - mLineWidth / 2;
        float endX = viewRect.height() / 2 + mLineWidth / 2;

        int centerX = viewRect.width() / 2;
        int centerY = viewRect.height() / 2;

        if (timelineType == TimelineType.START) {
            canvas.drawRect(startX, centerY, endX, viewRect.bottom, linePaint);
            drawStart(canvas, firstPaint, centerX, centerY, mStartSize);
//            itemRect.set(centerX - mMiddleSize, centerY - mMiddleSize, centerX + mMiddleSize, centerY + mMiddleSize);
        } else if (timelineType == TimelineType.MIDDLE) {
            canvas.drawRect(startX, viewRect.top, endX, viewRect.bottom, linePaint);
            switch (timelineAlignment) {
                case START:
                    drawMiddle(canvas, middlePaint, centerX, viewRect.top + mMiddleSize, mMiddleSize);
                    break;
                case MIDDLE:
                default:
                    drawMiddle(canvas, middlePaint, centerX, centerY, mMiddleSize);
                    break;
                case END:
                    centerY = viewRect.bottom;
                    drawMiddle(canvas, middlePaint, centerX, viewRect.bottom - mMiddleSize, mMiddleSize);
                    break;
            }
//            itemRect.set(centerX - mMiddleSize, centerY - mMiddleSize, centerX + mMiddleSize, centerY + mMiddleSize);
        } else if (timelineType == TimelineType.END) {
            canvas.drawRect(startX, viewRect.top, endX, centerY, linePaint);
            drawEnd(canvas, lastPaint, centerX, centerY, mEndSize);
//            itemRect.set(centerX - mMiddleSize, centerY - mMiddleSize, centerX + mMiddleSize, centerY + mMiddleSize);
        } else {
            canvas.drawRect(startX, viewRect.top, endX, viewRect.bottom, linePaint);
        }
/*
        canvas.clipRect(itemRect);*/

        super.onDraw(canvas);
    }

    public void setLineColor(int mLineColor) {
        this.mLineColor = mLineColor;
        linePaint.setColor(mLineColor);
        invalidate();
    }

    public void setLineWidth(float mLineWidth) {
        this.mLineWidth = mLineWidth;
        invalidate();
    }

    public void setMiddleSize(float mMiddleSize) {
        this.mMiddleSize = mMiddleSize;
        invalidate();
    }

    public void setStartSize(float mStartSize) {
        this.mStartSize = mStartSize;
        invalidate();
    }

    public void setEndSize(float mEndSize) {
        this.mEndSize = mEndSize;
        invalidate();
    }

    public void setItemColor(int color) {
        mColorMiddle = mFirstColor = mLastColor = color;
        firstPaint.setColor(mFirstColor);
        middlePaint.setColor(mColorMiddle);
        lastPaint.setColor(mLastColor);
        invalidate();
    }

    public void setItemSize(int size) {
        mMiddleSize = mStartSize = mEndSize = size;
        invalidate();
    }

    public void setTimelineType(TimelineType timelineType) {
        this.timelineType = timelineType;
        invalidate();
    }

    public void setTimelineAlignment(TimelineAlignment timelineAlignment) {
        this.timelineAlignment = timelineAlignment;
        invalidate();
    }

    protected abstract void drawStart(Canvas canvas, Paint firstPaint, float centerX, float centerY, float mStartSize);

    protected abstract void drawMiddle(Canvas canvas, Paint middlePaint, float centerX, float centerY, float mMiddleSize);

    protected abstract void drawEnd(Canvas canvas, Paint lastPaint, float centerX, float centerY, float mEndSize);
}
