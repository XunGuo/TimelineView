package com.alorma.timeline.painter.point

import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import com.alorma.timeline.painter.Painter
import com.alorma.timeline.property.Property

class PointPainter : Painter {

    lateinit var paint: Paint

    private fun createPaint(): Paint = Paint().apply {
        flags = Paint.ANTI_ALIAS_FLAG
        color = Color.WHITE
        strokeWidth = 8f
        style = Paint.Style.STROKE

    }


    override fun initProperties(typedArray: TypedArray) {
        paint = createPaint()
    }

    override fun <T> updateProperty(property: Property<T>) {

        paint = createPaint()
    }

    override fun draw(canvas: Canvas, rect: Rect) {
        canvas.drawCircle(
                rect.centerX().toFloat(),
                rect.centerY().toFloat(),
                24f,
                paint
        )
    }
}