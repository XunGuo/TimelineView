package com.alorma.timeline;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
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
import android.support.annotation.Size;
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

    private int lineStyle;
    private int lineColor;
    private float lineWidth;

    private int lineStartColor;
    private float lineStartSize;

    private int lineMiddleColor;
    private float lineMiddleSize;

    private int lineEndColor;
    private float lineEndSize;

    private int internalColor;
    private float internalPadding;
    private boolean drawInternal;

    private int timelineType;
    private int timelineAlignment;

    private Paint paintLine;
    private Paint paintMiddle;
    private Paint paintStart;
    private Paint paintEnd;
    private Paint paintInternal;

    private Rect rect;

    private float[] dashEffect;

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
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        Resources res = getResources();

        final TypedArray typedArray =
            context.getTheme().obtainStyledAttributes(attrs, R.styleable.TimelineView, defStyle, 0);

        lineColor = typedArray.getColor(R.styleable.TimelineView_timeline_lineColor,
            AttributesUtils.colorPrimary(context, res.getColor(R.color.colorPrimary)));
        lineWidth = typedArray.getDimension(R.styleable.TimelineView_timeline_lineWidth,
            res.getDimensionPixelOffset(R.dimen.default_lineWidth));
        lineStyle = getTimelineStyle(
            typedArray.getInt(R.styleable.TimelineView_timeline_lineStyle, STYLE_DEFAULT));

        lineStartColor = typedArray.getColor(R.styleable.TimelineView_timeline_startColor,
            AttributesUtils.colorAccent(context, res.getColor(R.color.colorAccent)));
        lineStartSize = typedArray.getFloat(R.styleable.TimelineView_timeline_startSize,
            res.getDimensionPixelOffset(R.dimen.default_itemSize));

        lineMiddleColor =
            typedArray.getColor(R.styleable.TimelineView_timeline_middleColor, lineStartColor);
        lineMiddleSize = typedArray.getFloat(R.styleable.TimelineView_timeline_middleSize,
            res.getDimensionPixelOffset(R.dimen.default_itemSize));

        lineEndColor =
            typedArray.getColor(R.styleable.TimelineView_timeline_endColor, lineStartColor);
        lineEndSize = typedArray.getFloat(R.styleable.TimelineView_timeline_endSize,
            res.getDimensionPixelOffset(R.dimen.default_itemSize));

        internalColor = typedArray.getColor(R.styleable.TimelineView_timeline_internalColor,
            AttributesUtils.windowBackground(context, Color.WHITE));
        internalPadding = typedArray.getFloat(R.styleable.TimelineView_timeline_internalPadding,
            res.getDimensionPixelOffset(R.dimen.default_internalPadding));

        timelineType = getTimelineType(
            typedArray.getInt(R.styleable.TimelineView_timeline_type, TYPE_DEFAULT));

        timelineAlignment = getTimelineAlignment(
            typedArray.getInt(R.styleable.TimelineView_timeline_alignment, ALIGNMENT_DEFAULT));

        drawInternal = typedArray.getBoolean(R.styleable.TimelineView_timeline_drawInternal,
            res.getBoolean(R.bool.default_drawInternal));

        typedArray.recycle();

        paintLine = new Paint();
        paintLine.setFlags(Paint.ANTI_ALIAS_FLAG);
        paintLine.setColor(lineColor);
        paintLine.setStrokeWidth(lineWidth);
        paintLine.setStyle(Paint.Style.STROKE);
        setDashEffect(new float[] { 25, 20 });

        paintStart = new Paint();
        paintStart.setFlags(Paint.ANTI_ALIAS_FLAG);
        paintStart.setColor(lineStartColor);
        paintStart.setStyle(Paint.Style.FILL);

        paintMiddle = new Paint();
        paintMiddle.setFlags(Paint.ANTI_ALIAS_FLAG);
        paintMiddle.setColor(lineMiddleColor);
        paintMiddle.setStyle(Paint.Style.FILL);

        paintEnd = new Paint();
        paintEnd.setFlags(Paint.ANTI_ALIAS_FLAG);
        paintEnd.setColor(lineEndColor);
        paintEnd.setStyle(Paint.Style.FILL);

        paintInternal = new Paint();
        paintInternal.setFlags(Paint.ANTI_ALIAS_FLAG);
        paintInternal.setColor(internalColor);
        paintInternal.setStyle(Paint.Style.FILL);

        rect = new Rect();
    }

    @Override protected void onDraw(Canvas canvas) {
        canvas.getClipBounds(rect);

        if (timelineType == TYPE_START) {
            canvas.drawLine(rect.centerX(), rect.centerY(), rect.centerX(), rect.bottom, paintLine);
            drawStart(canvas, paintStart, rect.centerX(), rect.centerY(), lineStartSize);
            if (drawInternal) {
                drawInternalStart(canvas, paintInternal, rect.centerX(), rect.centerY(),
                    lineStartSize - internalPadding);
            }
        } else if (timelineType == TYPE_MIDDLE) {
            canvas.drawLine(rect.centerX(), rect.top, rect.centerX(), rect.bottom, paintLine);
            int centerY = rect.centerY();
            switch (timelineAlignment) {
                case ALIGNMENT_START:
                    centerY = (int) (rect.top + lineMiddleSize);
                    break;
                case ALIGNMENT_END:
                    centerY = (int) (rect.bottom - lineMiddleSize);
                    break;
            }
            drawMiddle(canvas, paintMiddle, rect.centerX(), centerY, lineMiddleSize);
            if (drawInternal) {
                drawInternalMiddle(canvas, paintInternal, rect.centerX(), centerY,
                    lineMiddleSize - internalPadding);
            }
        } else if (timelineType == TYPE_END) {
            canvas.drawLine(rect.centerX(), rect.top, rect.centerX(), rect.centerY(), paintLine);
            drawEnd(canvas, paintEnd, rect.centerX(), rect.centerY(), lineEndSize);
            if (drawInternal) {
                drawInternalEnd(canvas, paintInternal, rect.centerX(), rect.centerY(),
                    lineMiddleSize - internalPadding);
            }
        } else {
            canvas.drawLine(rect.centerX(), rect.top, rect.centerX(), rect.bottom, paintLine);
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
        this.lineColor = mLineColor;
        paintLine.setColor(mLineColor);
        invalidate();
    }

    public void setLineWidth(float mLineWidth) {
        this.lineWidth = mLineWidth;
        invalidate();
    }

    public void setMiddleSize(float mMiddleSize) {
        this.lineMiddleSize = mMiddleSize;
        invalidate();
    }

    public void setStartSize(float mStartSize) {
        this.lineStartSize = mStartSize;
        invalidate();
    }

    public void setEndSize(float mEndSize) {
        this.lineEndSize = mEndSize;
        invalidate();
    }

    public void setItemColor(@ColorInt int color) {
        lineMiddleColor = lineStartColor = lineEndColor = color;
        paintStart.setColor(lineStartColor);
        paintMiddle.setColor(lineMiddleColor);
        paintEnd.setColor(lineEndColor);
        invalidate();
    }

    public void setInternalColor(@ColorInt int color) {
        internalColor = color;
        paintInternal.setColor(internalColor);
        invalidate();
    }

    public void setItemSize(int size) {
        lineMiddleSize = lineStartSize = lineEndSize = size;
        invalidate();
    }

    public void setTimelineStyle(@TimelineStyle int timelineStyle) {
        if (timelineStyle == STYLE_DASHED) {
            paintLine.setPathEffect(createDashEffect());
        } else {
            paintLine.setPathEffect(null);
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

    public float[] getDashEffect() {
        return dashEffect;
    }

    public void setDashEffect(@Size(2) float[] dashEffect) {
        this.dashEffect = dashEffect;
        if (lineStyle == STYLE_DASHED) {
            paintLine.setPathEffect(createDashEffect());
        }
    }

    private PathEffect createDashEffect() {
        return new DashPathEffect(dashEffect, 1);
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