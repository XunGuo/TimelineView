package com.alorma.timeline;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.PathEffect;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.IntDef;
import android.support.annotation.Size;
import android.util.AttributeSet;
import android.view.View;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public abstract class TimelineView extends View {
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

    private float indicatorSize;

    private float internalPadding;
    private boolean drawInternal;
    Drawable internalDrawable;
    Bitmap internalBitmapCache;

    private int timelineType;
    private int timelineAlignment;

    private Paint paintLine;
    private Paint paintIndicator;
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

    @SuppressWarnings("deprecation")
    private void init(Context context, AttributeSet attrs, int defStyle) {
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        Resources res = getResources();

        final TypedArray typedArray =
            context.getTheme().obtainStyledAttributes(attrs, R.styleable.TimelineView, defStyle, 0);

        int lineColor = typedArray.getColor(R.styleable.TimelineView_timeline_lineColor,
            AttributesUtils.colorPrimary(context, res.getColor(R.color.colorPrimary)));
        float lineWidth = typedArray.getDimension(R.styleable.TimelineView_timeline_lineWidth,
            res.getDimensionPixelOffset(R.dimen.default_lineWidth));
        lineStyle = getTimelineStyle(
            typedArray.getInt(R.styleable.TimelineView_timeline_lineStyle, STYLE_DEFAULT));

        int indicatorColor = typedArray.getColor(R.styleable.TimelineView_timeline_indicatorColor,
            AttributesUtils.colorAccent(context, res.getColor(R.color.colorAccent)));
        indicatorSize = typedArray.getDimension(R.styleable.TimelineView_timeline_indicatorSize,
            res.getDimensionPixelOffset(R.dimen.default_itemSize));

        drawInternal = typedArray.getBoolean(R.styleable.TimelineView_timeline_drawInternal,
            res.getBoolean(R.bool.default_drawInternal));
        int internalColor = typedArray.getColor(R.styleable.TimelineView_timeline_internalColor,
            AttributesUtils.windowBackground(context, Color.WHITE));
        internalPadding = typedArray.getDimension(R.styleable.TimelineView_timeline_internalPadding,
            res.getDimensionPixelOffset(R.dimen.default_internalPadding));
        if (!isInEditMode()) {
            internalDrawable =
                typedArray.getDrawable(R.styleable.TimelineView_timeline_internalDrawable);
        }

        timelineType = getTimelineType(
            typedArray.getInt(R.styleable.TimelineView_timeline_type, TYPE_DEFAULT));

        timelineAlignment = getTimelineAlignment(
            typedArray.getInt(R.styleable.TimelineView_timeline_alignment, ALIGNMENT_DEFAULT));

        typedArray.recycle();

        paintLine = new Paint();
        paintLine.setFlags(Paint.ANTI_ALIAS_FLAG);
        paintLine.setColor(lineColor);
        paintLine.setStrokeWidth(lineWidth);
        paintLine.setStyle(Paint.Style.STROKE);
        dashEffect = new float[] { 25, 20 };
        if (lineStyle == STYLE_DASHED) {
            paintLine.setPathEffect(createDashEffect());
        }

        paintIndicator = new Paint();
        paintIndicator.setFlags(Paint.ANTI_ALIAS_FLAG);
        paintIndicator.setColor(indicatorColor);
        paintIndicator.setStyle(Paint.Style.FILL);

        paintInternal = new Paint();
        paintInternal.setFlags(Paint.ANTI_ALIAS_FLAG);
        paintInternal.setColor(internalColor);
        paintInternal.setStyle(Paint.Style.FILL);

        rect = new Rect();
    }

    @Override protected void onDraw(Canvas canvas) {
        canvas.getClipBounds(rect);

        switch (timelineType) {
            case TYPE_START:
                canvas.drawLine(rect.centerX(), rect.centerY(), rect.centerX(), rect.bottom,
                    paintLine);
                drawIndicator(canvas, paintIndicator, rect.centerX(), rect.centerY(),
                    indicatorSize);
                if (drawInternal) {
                    drawInternal(canvas, paintInternal, rect.centerX(), rect.centerY(),
                        indicatorSize - internalPadding);
                }
                if (internalDrawable != null) {
                    drawBitmap(canvas, (rect.centerX() - indicatorSize) + internalPadding,
                        (rect.centerY() - indicatorSize) + internalPadding,
                        (int) ((indicatorSize - internalPadding) * 2));
                }
                break;

            case TYPE_MIDDLE:
                canvas.drawLine(rect.centerX(), rect.top, rect.centerX(), rect.bottom, paintLine);
                int centerY = rect.centerY();
                if (timelineAlignment == ALIGNMENT_START) {
                    centerY = (int) (rect.top + indicatorSize);
                } else if (timelineAlignment == ALIGNMENT_END) {
                    centerY = (int) (rect.bottom - indicatorSize);
                }
                drawIndicator(canvas, paintIndicator, rect.centerX(), centerY, indicatorSize);
                if (drawInternal) {
                    drawInternal(canvas, paintInternal, rect.centerX(), centerY,
                        indicatorSize - internalPadding);
                }
                if (internalDrawable != null) {
                    drawBitmap(canvas, (rect.centerX() - indicatorSize) + internalPadding,
                        (centerY - indicatorSize) + internalPadding,
                        (int) ((indicatorSize - internalPadding) * 2));
                }
                break;

            case TYPE_END:
                canvas.drawLine(rect.centerX(), rect.top, rect.centerX(), rect.centerY(),
                    paintLine);
                drawIndicator(canvas, paintIndicator, rect.centerX(), rect.centerY(),
                    indicatorSize);
                if (drawInternal) {
                    drawInternal(canvas, paintInternal, rect.centerX(), rect.centerY(),
                        indicatorSize - internalPadding);
                }
                if (internalDrawable != null) {
                    drawBitmap(canvas, (rect.centerX() - indicatorSize) + internalPadding,
                        (rect.centerY() - indicatorSize) + internalPadding,
                        (int) ((indicatorSize - internalPadding) * 2));
                }
                break;

            default:
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

    public int getLineColor() {
        return paintLine.getColor();
    }

    public void setLineColor(@ColorInt int lineColor) {
        paintLine.setColor(lineColor);
        invalidate();
    }

    public float getLineWidth() {
        return paintLine.getStrokeWidth();
    }

    public void setLineWidth(float lineWidth) {
        paintLine.setStrokeWidth(lineWidth);
        invalidate();
    }

    public int getIndicatorColor() {
        return paintIndicator.getColor();
    }

    public void setIndicatorColor(@ColorInt int indicatorColor) {
        paintIndicator.setColor(indicatorColor);
        invalidate();
    }

    public float getIndicatorSize() {
        return indicatorSize;
    }

    public void setIndicatorSize(float indicatorSize) {
        this.indicatorSize = indicatorSize;
        invalidate();
    }

    public int getInternalColor() {
        return paintInternal.getColor();
    }

    public void setInternalColor(@ColorInt int internalColor) {
        paintInternal.setColor(internalColor);
        invalidate();
    }

    public float getInternalPadding() {
        return internalPadding;
    }

    public void setInternalPadding(float internalPadding) {
        this.internalPadding = internalPadding;
        invalidate();
    }

    public boolean isDrawInternal() {
        return drawInternal;
    }

    public void setDrawInternal(boolean drawInternal) {
        this.drawInternal = drawInternal;
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
        invalidate();
    }

    private PathEffect createDashEffect() {
        return new DashPathEffect(dashEffect, 1);
    }

    public Paint getPaintLine() {
        return paintLine;
    }

    public Paint getPaintIndicator() {
        return paintIndicator;
    }

    public Paint getPaintInternal() {
        return paintInternal;
    }

    public void setTimelineStyle(@TimelineStyle int timelineStyle) {
        if (timelineStyle == STYLE_DASHED) {
            paintLine.setPathEffect(createDashEffect());
        } else {
            paintLine.setPathEffect(null);
        }
        invalidate();
    }

    public @TimelineStyle int getLineStyle() {
        return lineStyle;
    }

    public @TimelineType int getTimelineType() {
        return timelineType;
    }

    public void setTimelineType(@TimelineType int timelineType) {
        this.timelineType = timelineType;
        invalidate();
    }

    public @TimelineAlignment int getTimelineAlignment() {
        return timelineAlignment;
    }

    public void setTimelineAlignment(@TimelineAlignment int timelineAlignment) {
        this.timelineAlignment = timelineAlignment;
        invalidate();
    }

    public void setInternalDrawable(Drawable internalDrawable) {
        this.internalDrawable = internalDrawable;
        if (internalBitmapCache != null) {
            internalBitmapCache.recycle();
            internalBitmapCache = null;
        }
        invalidate();
    }

    protected abstract void drawIndicator(Canvas canvas, Paint paintStart, float centerX,
        float centerY, float size);

    protected abstract void drawInternal(Canvas canvas, Paint paintInternal, float centerX,
        float centerY, float size);

    protected abstract void drawBitmap(Canvas canvas, float left, float top, int size);
}