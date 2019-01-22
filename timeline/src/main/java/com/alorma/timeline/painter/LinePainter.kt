package com.alorma.timeline.painter

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import com.alorma.timeline.AttributesUtils
import com.alorma.timeline.R
import com.alorma.timeline.painter.line.DashedLinePainter
import com.alorma.timeline.painter.line.LineStylePainter
import com.alorma.timeline.painter.line.LinearLinePainter
import com.alorma.timeline.property.LineColor
import com.alorma.timeline.property.LineStyle
import com.alorma.timeline.property.Property

class LinePainter(val context: Context) : Painter {

    private var currentPainter: LineStylePainter = LinearLinePainter(context)

    private var lineWidth: Float = context.resources.getDimensionPixelOffset(R.dimen.default_lineWidth).toFloat()
    private var lineColor: Int = AttributesUtils.colorPrimary(context, Color.GRAY)
    lateinit var paint: Paint

    private fun createPaint(): Paint = Paint().apply {
        flags = Paint.ANTI_ALIAS_FLAG
        color = lineColor
        strokeWidth = lineWidth
        style = Paint.Style.STROKE
        currentPainter.paintLineStyle(this)
    }

    override fun initProperties(typedArray: TypedArray) {
        currentPainter = when (typedArray.getInt(R.styleable.TimelineView_timeline_lineStyle,
                STYLE_LINEAR)) {
            STYLE_DASHED -> DashedLinePainter(context)
            else -> LinearLinePainter(context)
        }
        lineWidth = typedArray.getDimension(R.styleable.TimelineView_timeline_lineWidth,
                lineWidth)
        lineColor = typedArray.getColor(R.styleable.TimelineView_timeline_lineColor,
                lineColor)

        paint = createPaint()
    }

    override fun <T> updateProperty(property: Property<T>) {
        when (property) {
            is LineStyle -> {
                currentPainter = when (property) {
                    is LineStyle.LINEAR -> LinearLinePainter(context)
                    is LineStyle.DASHED -> DashedLinePainter(context)
                }
            }
            is LineColor -> lineColor = property.lineColor
        }
        paint = createPaint()
    }

    override fun draw(canvas: Canvas, rect: Rect) {
        currentPainter.draw(canvas, rect, paint)
    }

    companion object {
        const val STYLE_DASHED = -1
        const val STYLE_LINEAR = 0
    }
}