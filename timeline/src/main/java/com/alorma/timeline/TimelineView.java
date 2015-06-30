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
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public abstract class TimelineView extends View {

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
        middlePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        middlePaint.setStrokeWidth(mMiddleSize);

        firstPaint = new Paint();
        firstPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        firstPaint.setColor(mFirstColor);
        firstPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        firstPaint.setStrokeWidth(mStartSize);

        lastPaint = new Paint();
        lastPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        lastPaint.setColor(mLastColor);
        lastPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        lastPaint.setStrokeWidth(mEndSize);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int contentWidth = getWidth() - getPaddingLeft() - getPaddingRight();
        int contentHeight = getHeight() - getPaddingTop() - getPaddingBottom();

        float startX = contentWidth / 2 - mLineWidth / 2;
        float endX = contentWidth / 2 + mLineWidth / 2;
        int startY = getPaddingTop();

        int centerX = contentWidth / 2;
        int centerY = contentHeight / 2;

        if (timelineType == TimelineType.START) {
            canvas.drawRect(startX, centerY, endX, contentHeight, linePaint);
            drawStart(canvas, firstPaint, centerX, centerY, mStartSize);
        } else if (timelineType == TimelineType.MIDDLE) {
            canvas.drawRect(startX, startY, endX, contentHeight, linePaint);
            switch (timelineAlignment) {
                case START:
                    startY += (mMiddleSize * 2);
                    drawMiddle(canvas, middlePaint, centerX, startY, mMiddleSize);
                    break;
                case MIDDLE:
                default:
                    drawMiddle(canvas, middlePaint, centerX, centerY, mMiddleSize);
                    break;
                case END:
                    contentHeight -= (mMiddleSize * 2);
                    drawMiddle(canvas, middlePaint, centerX, contentHeight, mMiddleSize);
                    break;
            }
        } else if (timelineType == TimelineType.END) {
            canvas.drawRect(startX, startY, endX, centerY, linePaint);
            drawEnd(canvas, lastPaint, centerX, centerY, mEndSize);
        } else {
            canvas.drawRect(startX, startY, endX, contentHeight, linePaint);
        }
    }

    public void setmLineColor(int mLineColor) {
        this.mLineColor = mLineColor;
        linePaint.setColor(mLineColor);
        invalidate();
    }

    public void setmLineWidth(float mLineWidth) {
        this.mLineWidth = mLineWidth;
        invalidate();
    }

    public void setmColorMiddle(int mColorMiddle) {
        this.mColorMiddle = mColorMiddle;
        invalidate();
    }

    public void setmMiddleSize(float mMiddleSize) {
        this.mMiddleSize = mMiddleSize;
        invalidate();
    }

    public void setmFirstColor(int mFirstColor) {
        this.mFirstColor = mFirstColor;
        invalidate();
    }

    public void setmStartSize(float mStartSize) {
        this.mStartSize = mStartSize;
        invalidate();
    }

    public void setmLastColor(int mLastColor) {
        this.mLastColor = mLastColor;
        invalidate();
    }

    public void setmEndSize(float mEndSize) {
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

/*    private int fetchPrimaryColor() {
        boolean useAppCompat = true;

        int colorPrimary = Color.parseColor("#FF0000");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            TypedArray a = getContext().getTheme().obtainStyledAttributes(new int[]{android.R.attr.colorPrimary});
            try {
                colorPrimary = a.getColor(0, colorPrimary);
                useAppCompat = false;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                a.recycle();
            }
        }

        if (useAppCompat) {
            TypedArray a = getContext().getTheme().obtainStyledAttributes(new int[]{R.attr.colorPrimary});
            try {
                colorPrimary = a.getColor(0, colorPrimary);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                a.recycle();
            }
        }

        return colorPrimary;
    }

    private int fetchAccentColor() {
        boolean useAppCompat = true;

        int colorAccent = Color.parseColor("#00FF00");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            TypedArray a = getContext().getTheme().obtainStyledAttributes(new int[]{android.R.attr.colorAccent});
            try {
                colorAccent = a.getColor(0, colorAccent);
                useAppCompat = false;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                a.recycle();
            }
        }

        if (useAppCompat) {
            TypedArray a = getContext().getTheme().obtainStyledAttributes(new int[]{R.attr.colorAccent});
            try {
                colorAccent = a.getColor(0, colorAccent);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                a.recycle();
            }
        }

        return colorAccent;
    }*/
}
