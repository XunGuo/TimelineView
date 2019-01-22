package com.alorma.timeline.painter.point

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect

class SquarePointStylePainter : PointStylePainter() {

    private val fillPaint: Paint by lazy {
        Paint().apply {
            flags = Paint.ANTI_ALIAS_FLAG
            color = Color.GRAY
            style = Paint.Style.FILL
        }
    }

    private val strokesPaint: Paint by lazy {
        Paint().apply {
            flags = Paint.ANTI_ALIAS_FLAG
            color = Color.WHITE
            strokeWidth = 8f
            style = Paint.Style.STROKE
        }
    }

    override fun draw(canvas: Canvas, rect: Rect) {
        val strokeExtra = 45
        val strokeRect = Rect(
                rect.centerX() - strokeExtra,
                rect.centerY() - strokeExtra,
                rect.centerX() + strokeExtra,
                rect.centerY() + strokeExtra
        )
        canvas.drawRect(strokeRect, strokesPaint)

        val boxExtra = 20
        val boxRect = Rect(
                rect.centerX() - boxExtra,
                rect.centerY() - boxExtra,
                rect.centerX() + boxExtra,
                rect.centerY() + boxExtra
        )
        canvas.drawRect(boxRect, fillPaint)
    }
}