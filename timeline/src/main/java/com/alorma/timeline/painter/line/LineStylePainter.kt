package com.alorma.timeline.painter.line

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import com.alorma.timeline.property.LineVerticalPosition

abstract class LineStylePainter {

    abstract fun paintLineStyle(paint: Paint)

    fun draw(canvas: Canvas, rect: Rect, linePosition: LineVerticalPosition, paint: Paint) {
        val useRect = when (linePosition) {
            LineVerticalPosition.START -> rect.startRect()
            LineVerticalPosition.END -> rect.endRect()
            LineVerticalPosition.FULL -> rect
        }
        canvas.drawLine(
                useRect.centerX().toFloat(),
                useRect.top.toFloat(),
                useRect.centerX().toFloat(),
                useRect.bottom.toFloat(),
                paint
        )
    }

    private fun Rect.startRect(): Rect = Rect(left, centerY(), right, bottom)

    private fun Rect.endRect(): Rect = Rect(left, top, right, centerY())
}