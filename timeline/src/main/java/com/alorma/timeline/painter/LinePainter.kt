package com.alorma.timeline.painter

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import com.alorma.timeline.AttributesUtils
import com.alorma.timeline.R

class LinePainter(context: Context) : Painter {

    private var linerStyle: Int = STYLE_LINEAR
    private var lineWidth: Float = context.resources.getDimensionPixelOffset(R.dimen.default_lineWidth).toFloat()
    private var lineColor: Int = AttributesUtils.colorPrimary(context, Color.GRAY)
    private val paint: Paint by lazy {
        Paint().apply {
            flags = Paint.ANTI_ALIAS_FLAG
            color = lineColor
            strokeWidth = lineWidth
            style = Paint.Style.STROKE
        }
    }


    override fun initProperties(typedArray: TypedArray) {
        linerStyle = typedArray.getInt(R.styleable.TimelineView_timeline_lineStyle,
                linerStyle)
        lineWidth = typedArray.getDimension(R.styleable.TimelineView_timeline_lineWidth,
                lineWidth)
        lineColor = typedArray.getColor(R.styleable.TimelineView_timeline_lineColor,
                lineColor)
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
        val STYLE_DASHED = -1
        val STYLE_LINEAR = 0
    }
}