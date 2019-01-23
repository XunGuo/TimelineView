package com.alorma.timeline.painter.line

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect

abstract class LineStylePainter {

    abstract fun paintLineStyle(paint: Paint)

    fun draw(canvas: Canvas,
             rect: Rect,
             paint: Paint) {

        canvas.drawLine(
                rect.centerX().toFloat(),
                rect.top.toFloat(),
                rect.centerX().toFloat(),
                rect.bottom.toFloat(),
                paint
        )
    }
}