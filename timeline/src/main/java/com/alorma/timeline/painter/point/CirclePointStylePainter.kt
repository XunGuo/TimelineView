package com.alorma.timeline.painter.point

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect

class CirclePointStylePainter : PointStylePainter() {

    private val circleFillPaint: Paint by lazy {
        Paint().apply {
            flags = Paint.ANTI_ALIAS_FLAG
            color = Color.GRAY
            style = Paint.Style.FILL
        }
    }

    private val circleStrokesPaint: Paint by lazy {
        Paint().apply {
            flags = Paint.ANTI_ALIAS_FLAG
            color = Color.WHITE
            strokeWidth = 8f
            style = Paint.Style.STROKE
        }
    }

    override fun draw(canvas: Canvas, rect: Rect) {
        canvas.drawCircle(
                rect.centerX().toFloat(),
                rect.centerY().toFloat(),
                24f,
                circleFillPaint
        )
        canvas.drawCircle(
                rect.centerX().toFloat(),
                rect.centerY().toFloat(),
                28f,
                circleStrokesPaint
        )
    }
}