package com.alorma.timeline;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.PathEffect;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.IntDef;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public abstract class TimelineView extends ImageView {
    public static final int TYPE_START = -1;
    public static final int TYPE_MIDDLE = 0;
    public static final int TYPE_LINE = 1;
    public static final int TYPE_END = 2;
    public static final int TYPE_DEFAULT = TYPE_MIDDLE;

    @Retention(RetentionPolicy.SOURCE) @IntDef({ TYPE_START, TYPE_MIDDLE, TYPE_LINE, TYPE_END })
    public @interface TimelineType {
    }

    public static final int ALIGNMENT_START = -1;
    public static final int ALIGNMENT_MIDDLE = 0;
    public static final int ALIGNMENT_END = 1;
    public static final int ALIGNMENT_DEFAULT = ALIGNMENT_MIDDLE;

    @Retention(RetentionPolicy.SOURCE) @IntDef({ ALIGNMENT_START, ALIGNMENT_MIDDLE, ALIGNMENT_END })
    public @interface TimelineAlignment {
    }

    public static final int STYLE_DASHED = -1;
    public static final int STYLE_LINEAR = 0;
    public static final int STYLE_DEFAULT = STYLE_LINEAR;

    @Retention(RetentionPolicy.SOURCE) @IntDef({ STYLE_DASHED, STYLE_LINEAR })
    public @interface TimelineStyle {
    }

    // TODO: remove hungarian notation
    private int mLineColor = Color.GRAY;
    private float mLineWidth = 3f;
    private int lineStyle = STYLE_DEFAULT;

    private int mColorMiddle = -1;
    private float mMiddleSize = 2f;

    private int mFirstColor = -1;
    private float mStartSize = 2f;

    private int mLastColor = -1;
    private float mEndSize = 2f;

    private int mInternalColor = -1;
    private float mInternalPadding = 2f;

    private int timelineType = TYPE_DEFAULT;
    private int timelineAlignment = ALIGNMENT_DEFAULT;

    private Paint linePaint, middlePaint, firstPaint, lastPaint, internalPaint;
    private Rect viewRect;

    private boolean drawInternal = false;

    public TimelineView(Context context) {
        this(context, null);
    }

    public TimelineView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TimelineView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public TimelineView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyle) {
        isInEditMode();
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        mLineColor = AttributesUtils.colorPrimary(context, Color.parseColor("#ff0000"));
        mColorMiddle = mFirstColor =
            mLastColor = AttributesUtils.colorAccent(context, Color.parseColor("#00ff00"));
        mLineWidth =
            getContext().getResources().getDimensionPixelOffset(R.dimen.timeline_lineWidth);
        mMiddleSize = mStartSize = mEndSize =
            getContext().getResources().getDimensionPixelOffset(R.dimen.timeline_itemSize);

        mInternalColor = AttributesUtils.windowBackground(context, Color.WHITE);
        mInternalPadding =
            getContext().getResources().getDimensionPixelOffset(R.dimen.timeline_internalPadding);

        if (attrs != null) {
            final TypedArray a = context.getTheme()
                .obtainStyledAttributes(attrs, R.styleable.TimelineView, defStyle, 0);

            if (a != null) {
                mLineColor = a.getColor(R.styleable.TimelineView_timeline_lineColor, mLineColor);

                int lineStyle = a.getInt(R.styleable.TimelineView_timeline_lineStyle, 0);
                this.lineStyle = getTimelineStyle(lineStyle);

                mLineWidth =
                    a.getDimension(R.styleable.TimelineView_timeline_lineWidth, mLineWidth);

                mColorMiddle =
                    a.getColor(R.styleable.TimelineView_timeline_middleColor, mColorMiddle);

                mMiddleSize = a.getFloat(R.styleable.TimelineView_timeline_middleSize, mMiddleSize);

                mFirstColor = a.getColor(R.styleable.TimelineView_timeline_firstColor, mFirstColor);

                mStartSize = a.getFloat(R.styleable.TimelineView_timeline_firstSize, mStartSize);

                mLastColor = a.getColor(R.styleable.TimelineView_timeline_lastColor, mLastColor);

                mEndSize = a.getFloat(R.styleable.TimelineView_timeline_lastSize, mEndSize);

                mInternalColor =
                    a.getColor(R.styleable.TimelineView_timeline_internalColor, mInternalColor);
                mInternalPadding =
                    a.getFloat(R.styleable.TimelineView_timeline_internalPadding, mInternalPadding);

                int type = a.getInt(R.styleable.TimelineView_timeline_type, 0);
                this.timelineType = getTimelineType(type);

                int alignment = a.getInt(R.styleable.TimelineView_timeline_alignment, 0);
                this.timelineAlignment = getTimelineAlignment(alignment);

                drawInternal =
                    a.getBoolean(R.styleable.TimelineView_timeline_drawInternal, drawInternal);

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

        if (lineStyle == STYLE_DASHED) {
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
        // TODO: custom pattern
        return new DashPathEffect(new float[] { 25, 20 }, 1);
    }

    @Override protected void onDraw(Canvas canvas) {
        canvas.getClipBounds(viewRect);

        if (timelineType == TYPE_START) {
            canvas.drawLine(viewRect.centerX(), viewRect.centerY(), viewRect.centerX(),
                viewRect.bottom, linePaint);
            drawStart(canvas, firstPaint, viewRect.centerX(), viewRect.centerY(), mStartSize);
            if (drawInternal) {
                drawInternalStart(canvas, internalPaint, viewRect.centerX(), viewRect.centerY(),
                    mStartSize - mInternalPadding);
            }
        } else if (timelineType == TYPE_MIDDLE) {
            canvas.drawLine(viewRect.centerX(), viewRect.top, viewRect.centerX(), viewRect.bottom,
                linePaint);
            int centerY = viewRect.centerY();
            switch (timelineAlignment) {
                case ALIGNMENT_START:
                    centerY = (int) (viewRect.top + mMiddleSize);
                    break;
                case ALIGNMENT_END:
                    centerY = (int) (viewRect.bottom - mMiddleSize);
                    break;
            }
            drawMiddle(canvas, middlePaint, viewRect.centerX(), centerY, mMiddleSize);
            if (drawInternal) {
                drawInternalMiddle(canvas, internalPaint, viewRect.centerX(), centerY,
                    mMiddleSize - mInternalPadding);
            }
        } else if (timelineType == TYPE_END) {
            canvas.drawLine(viewRect.centerX(), viewRect.top, viewRect.centerX(),
                viewRect.centerY(), linePaint);
            drawEnd(canvas, lastPaint, viewRect.centerX(), viewRect.centerY(), mEndSize);
            if (drawInternal) {
                drawInternalEnd(canvas, internalPaint, viewRect.centerX(), viewRect.centerY(),
                    mMiddleSize - mInternalPadding);
            }
        } else {
            canvas.drawLine(viewRect.centerX(), viewRect.top, viewRect.centerX(), viewRect.bottom,
                linePaint);
        }

        super.onDraw(canvas);
    }

    private @TimelineType int getTimelineType(int value) {
        return value;
    }

    private @TimelineAlignment int getTimelineAlignment(int value) {
        return value;
    }

    private @TimelineStyle int getTimelineStyle(int value) {
        return value;
    }

    public void setLineColor(@ColorInt int mLineColor) {
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

    public void setItemColor(@ColorInt int color) {
        mColorMiddle = mFirstColor = mLastColor = color;
        firstPaint.setColor(mFirstColor);
        middlePaint.setColor(mColorMiddle);
        lastPaint.setColor(mLastColor);
        invalidate();
    }

    public void setInternalColor(@ColorInt int color) {
        mInternalColor = color;
        internalPaint.setColor(mInternalColor);
        invalidate();
    }

    public void setItemSize(int size) {
        mMiddleSize = mStartSize = mEndSize = size;
        invalidate();
    }

    public void setTimelineStyle(@TimelineStyle int timelineStyle) {
        if (timelineStyle == STYLE_DASHED) {
            linePaint.setPathEffect(createDashEffect());
        } else {
            linePaint.setPathEffect(null);
        }
    }

    public void setTimelineType(@TimelineType int timelineType) {
        this.timelineType = timelineType;
        invalidate();
    }

    public void setTimelineAlignment(@TimelineAlignment int timelineAlignment) {
        this.timelineAlignment = timelineAlignment;
        invalidate();
    }

    // TODO: GETTERS

    protected abstract void drawStart(Canvas canvas, Paint firstPaint, float centerX, float centerY,
        float mStartSize);

    protected abstract void drawMiddle(Canvas canvas, Paint middlePaint, float centerX,
        float centerY, float mMiddleSize);

    protected abstract void drawEnd(Canvas canvas, Paint lastPaint, float centerX, float centerY,
        float mEndSize);

    protected abstract void drawInternalStart(Canvas canvas, Paint internalPaint, float centerX,
        float centerY, float radius);

    protected abstract void drawInternalMiddle(Canvas canvas, Paint internalPaint, float centerX,
        float centerY, float radius);

    protected abstract void drawInternalEnd(Canvas canvas, Paint internalPaint, float centerX,
        float centerY, float radius);
}