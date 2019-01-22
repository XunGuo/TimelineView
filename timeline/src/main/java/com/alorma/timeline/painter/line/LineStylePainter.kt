package com.alorma.timeline.painter.line

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import com.alorma.timeline.property.LineHorizontalPosition
import com.alorma.timeline.property.LineVerticalPosition

abstract class LineStylePainter {

    abstract fun paintLineStyle(paint: Paint)

    fun draw(canvas: Canvas,
             rect: Rect,
             lineVPosition: LineVerticalPosition,
             lineHPosition: LineHorizontalPosition,
             paint: Paint) {

        val vRect = when (lineVPosition) {
            LineVerticalPosition.START -> rect.verticalStartRect()
            LineVerticalPosition.END -> rect.verticalEndRect()
            LineVerticalPosition.FULL -> rect
        }

        val useRect = when (lineHPosition) {
            LineHorizontalPosition.START -> {
                Rect(paint.strokeWidth.toInt(), vRect.top, (paint.strokeWidth * 2).toInt(), vRect.bottom)
            }
            LineHorizontalPosition.END -> {
                Rect(rect.right - paint.strokeWidth.toInt(),
                        vRect.top, rect.right - (paint.strokeWidth * 2).toInt(), vRect.bottom)
            }
            LineHorizontalPosition.CENTER -> vRect
        }

        canvas.drawLine(
                useRect.centerX().toFloat(),
                useRect.top.toFloat(),
                useRect.centerX().toFloat(),
                useRect.bottom.toFloat(),
                paint
        )
    }

    private fun Rect.verticalStartRect(): Rect = Rect(left, centerY(), right, bottom)

    private fun Rect.verticalEndRect(): Rect = Rect(left, top, right, centerY())
}