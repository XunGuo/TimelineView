package com.alorma.timeline.painter

import android.content.Context
import android.content.res.TypedArray
import android.graphics.*
import com.alorma.timeline.AttributesUtils
import com.alorma.timeline.R
import com.alorma.timeline.property.LineStyle
import com.alorma.timeline.property.Property

class LinePainter(context: Context) : Painter {

    private var linerStyle: Int = STYLE_LINEAR
    private var lineWidth: Float = context.resources.getDimensionPixelOffset(R.dimen.default_lineWidth).toFloat()
    private var lineColor: Int = AttributesUtils.colorPrimary(context, Color.GRAY)
    lateinit var paint: Paint

    private fun createPaint(): Paint = Paint().apply {
        flags = Paint.ANTI_ALIAS_FLAG
        color = lineColor
        strokeWidth = lineWidth
        style = Paint.Style.STROKE
        if (linerStyle == STYLE_DASHED) {
            val dashIntervals = floatArrayOf(25f, 20f)
            val dashPathEffect = DashPathEffect(dashIntervals, 1f)
            pathEffect = dashPathEffect
        }
    }

    override fun initProperties(typedArray: TypedArray) {
        linerStyle = typedArray.getInt(R.styleable.TimelineView_timeline_lineStyle,
                linerStyle)
        lineWidth = typedArray.getDimension(R.styleable.TimelineView_timeline_lineWidth,
                lineWidth)
        lineColor = typedArray.getColor(R.styleable.TimelineView_timeline_lineColor,
                lineColor)

        paint = createPaint()
    }

    override fun <T> updateProperty(property: Property<T>) {
        if (property is LineStyle) {
            linerStyle = when (property) {
                is LineStyle.LINEAR -> STYLE_LINEAR
                is LineStyle.DASHED -> STYLE_DASHED
            }
            paint = createPaint()
        }
    }

    override fun draw(canvas: Canvas, rect: Rect) {
        canvas.drawLine(
                rect.centerX().toFloat(),
                rect.top.toFloat(),
                rect.centerX().toFloat(),
                rect.bottom.toFloat(),
                paint
        )
    }

    companion object {
        const val STYLE_DASHED = -1
        const val STYLE_LINEAR = 0
    }
}