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
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.PathEffect;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

public abstract class TimelineView extends ImageView {

    private int mLineColor = Color.GRAY;
    private float mLineWidth = 3f;
    private LineStyle lineStyle = LineStyle.LINEAR;

    private int mColorMiddle = -1;
    private float mMiddleSize = 2f;

    private int mFirstColor = -1;
    private float mStartSize = 2f;

    private int mLastColor = -1;
    private float mEndSize = 2f;

    private int mInternalColor = -1;
    private float mInternalPadding = 2f;

    private TimelineType timelineType = TimelineType.MIDDLE;
    private TimelineAlignment timelineAlignment = TimelineAlignment.MIDDLE;

    private Paint linePaint, middlePaint, firstPaint, lastPaint, internalPaint;
    private Rect viewRect;

    private boolean drawInternal = false;

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
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        mLineColor = AttributesUtils.colorPrimary(context, Color.parseColor("#ff0000"));
        mColorMiddle = mFirstColor = mLastColor = AttributesUtils.colorAccent(context, Color.parseColor("#00ff00"));
        mLineWidth = getContext().getResources().getDimensionPixelOffset(R.dimen.timeline_lineWidth);
        mMiddleSize = mStartSize = mEndSize = getContext().getResources().getDimensionPixelOffset(R.dimen.timeline_itemSize);

        mInternalColor = AttributesUtils.windowBackground(context, Color.WHITE);
        mInternalPadding = getContext().getResources().getDimensionPixelOffset(R.dimen.timeline_internalPadding);

        if (attrs != null) {
            final TypedArray a = context.getTheme().obtainStyledAttributes(
                    attrs, R.styleable.TimelineView, defStyle, 0);

            if (a != null) {
                mLineColor = a.getColor(R.styleable.TimelineView_timeline_lineColor, mLineColor);

                int lineStyle = a.getInt(R.styleable.TimelineView_timeline_lineStyle, 0);

                this.lineStyle = LineStyle.fromId(lineStyle);

                mLineWidth = a.getDimension(R.styleable.TimelineView_timeline_lineWidth, mLineWidth);

                mColorMiddle = a.getColor(R.styleable.TimelineView_timeline_middleColor, mColorMiddle);

                mMiddleSize = a.getFloat(R.styleable.TimelineView_timeline_middleSize, mMiddleSize);

                mFirstColor = a.getColor(R.styleable.TimelineView_timeline_firstColor, mFirstColor);

                mStartSize = a.getFloat(R.styleable.TimelineView_timeline_firstSize, mStartSize);

                mLastColor = a.getColor(R.styleable.TimelineView_timeline_lastColor, mLastColor);

                mEndSize = a.getFloat(R.styleable.TimelineView_timeline_lastSize, mEndSize);

                mInternalColor = a.getColor(R.styleable.TimelineView_timeline_internalColor, mInternalColor);
                mInternalPadding = a.getFloat(R.styleable.TimelineView_timeline_internalPadding, mInternalPadding);

                int type = a.getInt(R.styleable.TimelineView_timeline_type, 0);

                this.timelineType = TimelineType.fromId(type);

                int alignment = a.getInt(R.styleable.TimelineView_timeline_alignment, 0);

                this.timelineAlignment = TimelineAlignment.fromId(alignment);

                drawInternal = a.getBoolean(R.styleable.TimelineView_timeline_drawInternal, drawInternal);

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

        if (mInternalColor == -1) {
            mInternalColor = mLineColor;
        }

        linePaint = new Paint();
        linePaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        linePaint.setColor(mLineColor);
        linePaint.setStrokeWidth(mLineWidth);
        linePaint.setStyle(Paint.Style.STROKE);

        if (lineStyle != null && lineStyle == LineStyle.DASHED) {
            linePaint.setPathEffect(createDashEffect());
        }

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

        internalPaint = new Paint();
        internalPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        internalPaint.setColor(mInternalColor);
        internalPaint.setStyle(Paint.Style.FILL);

        viewRect = new Rect();
    }

    private PathEffect createDashEffect() {
        return new DashPathEffect(new float[]{25, 20}, 1);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        canvas.getClipBounds(viewRect);

        if (timelineType == TimelineType.START) {
            canvas.drawLine(viewRect.centerX(), viewRect.centerY(), viewRect.centerX(), viewRect.bottom, linePaint);
            drawStart(canvas, firstPaint, viewRect.centerX(), viewRect.centerY(), mStartSize);
            if (drawInternal) {
                drawInternalStart(canvas, internalPaint, viewRect.centerX(), viewRect.centerY(), mStartSize - mInternalPadding);
            }
        } else if (timelineType == TimelineType.MIDDLE) {
            canvas.drawLine(viewRect.centerX(), viewRect.top, viewRect.centerX(), viewRect.bottom, linePaint);
            int centerY = viewRect.centerY();
            if (timelineAlignment != null) {
                switch (timelineAlignment) {
                    case START:
                        centerY = (int) (viewRect.top + mMiddleSize);
                        break;
                    case END:
                        centerY = (int) (viewRect.bottom - mMiddleSize);
                        break;
                }
            }
            drawMiddle(canvas, middlePaint, viewRect.centerX(), centerY, mMiddleSize);
            if (drawInternal) {
                drawInternalMiddle(canvas, internalPaint, viewRect.centerX(), centerY, mMiddleSize - mInternalPadding);
            }
        } else if (timelineType == TimelineType.END) {
            canvas.drawLine(viewRect.centerX(), viewRect.top, viewRect.centerX(), viewRect.centerY(), linePaint);
            drawEnd(canvas, lastPaint, viewRect.centerX(), viewRect.centerY(), mEndSize);
            if (drawInternal) {
                drawInternalEnd(canvas, internalPaint, viewRect.centerX(), viewRect.centerY(), mMiddleSize - mInternalPadding);
            }
        } else {
            canvas.drawLine(viewRect.centerX(), viewRect.top, viewRect.centerX(), viewRect.bottom, linePaint);
        }

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

    public void setInternalColor(int color) {
        mInternalColor = color;
        internalPaint.setColor(mInternalColor);
        invalidate();
    }

    public void setItemSize(int size) {
        mMiddleSize = mStartSize = mEndSize = size;
        invalidate();
    }

    public void setLineStyle(LineStyle lineStyle) {
        if (lineStyle != null) {
            if (lineStyle == LineStyle.DASHED) {
                linePaint.setPathEffect(createDashEffect());
            } else {
                linePaint.setPathEffect(null);
            }
        } else {
            linePaint.setPathEffect(null);
        }
    }

    public void setTimelineType(TimelineType timelineType) {
        if (timelineType == null) {
            timelineType = TimelineType.MIDDLE;
        }
        this.timelineType = timelineType;
        invalidate();
    }

    public void setTimelineAlignment(TimelineAlignment timelineAlignment) {
        if (timelineAlignment == null) {
            this.timelineAlignment = TimelineAlignment.MIDDLE;
        }
        this.timelineAlignment = timelineAlignment;
        invalidate();
    }

    protected abstract void drawStart(Canvas canvas, Paint firstPaint, float centerX, float centerY, float mStartSize);

    protected abstract void drawMiddle(Canvas canvas, Paint middlePaint, float centerX, float centerY, float mMiddleSize);

    protected abstract void drawEnd(Canvas canvas, Paint lastPaint, float centerX, float centerY, float mEndSize);

    protected abstract void drawInternalStart(Canvas canvas, Paint internalPaint, float centerX, float centerY, float radius);

    protected abstract void drawInternalMiddle(Canvas canvas, Paint internalPaint, float centerX, float centerY, float radius);

    protected abstract void drawInternalEnd(Canvas canvas, Paint internalPaint, float centerX, float centerY, float radius);
}
