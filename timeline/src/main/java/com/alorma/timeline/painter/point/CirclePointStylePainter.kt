package com.alorma.timeline.painter.point

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect

class CirclePointStylePainter : PointStylePainter() {

    private val circleFillPaint: Paint by lazy {
        Paint().apply {
            flags = Paint.ANTI_ALIAS_FLAG
            style = Paint.Style.FILL
        }
    }

    private val circleStrokesPaint: Paint by lazy {
        Paint().apply {
            flags = Paint.ANTI_ALIAS_FLAG
            strokeWidth = 8f
            style = Paint.Style.STROKE
        }
    }

    private var fillSize: Float = 0f

    override fun initColors(strokeColor: Int, fillColor: Int) {
        circleStrokesPaint.color = strokeColor
        circleFillPaint.color = fillColor
    }

    override fun initSizes(strokeSize: Float, fillSize: Float) {
        this.fillSize = fillSize
        circleStrokesPaint.strokeWidth = strokeSize
    }

    override fun draw(canvas: Canvas, rect: Rect) {
        canvas.drawCircle(
                rect.centerX().toFloat(),
                rect.centerY().toFloat(),
                fillSize / 2,
                circleFillPaint
        )
        canvas.drawCircle(
                rect.centerX().toFloat(),
                rect.centerY().toFloat(),
                fillSize / 2,
                circleStrokesPaint
        )
    }
}