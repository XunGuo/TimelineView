package com.alorma.timeline.painter.point

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF

class SquarePointStylePainter : PointStylePainter() {

    private val fillPaint: Paint by lazy {
        Paint().apply {
            flags = Paint.ANTI_ALIAS_FLAG
            style = Paint.Style.FILL
        }
    }

    private val strokesPaint: Paint by lazy {
        Paint().apply {
            flags = Paint.ANTI_ALIAS_FLAG
            style = Paint.Style.STROKE
        }
    }

    private var fillSize: Float = 0f

    override fun initColors(strokeColor: Int, fillColor: Int) {
        strokesPaint.color = strokeColor
        fillPaint.color = fillColor
    }

    override fun initSizes(strokeSize: Float, fillSize: Float) {
        this.fillSize = fillSize
        this.strokesPaint.strokeWidth = strokeSize
    }

    override fun draw(canvas: Canvas, rect: Rect) {
        val boxExtra = fillSize / 2
        val boxRect = RectF(
                rect.centerX() - boxExtra,
                rect.centerY() - boxExtra,
                rect.centerX() + boxExtra,
                rect.centerY() + boxExtra
        )
        canvas.drawRect(boxRect, fillPaint)

        val strokeExtra = (strokesPaint.strokeWidth / 2) + boxExtra
        val strokeRect = RectF(
                rect.centerX() - strokeExtra,
                rect.centerY() - strokeExtra,
                rect.centerX() + strokeExtra,
                rect.centerY() + strokeExtra
        )
        canvas.drawRect(strokeRect, strokesPaint)

    }
}