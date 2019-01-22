package com.alorma.timeline.painter.point

import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Rect
import com.alorma.timeline.painter.Painter
import com.alorma.timeline.property.Property

class PointPainter : Painter {

    private val circlePainter: PointStylePainter = CirclePointStylePainter()
    private val squarePainter: PointStylePainter = SquarePointStylePainter()
    private val currentPainter: PointStylePainter = squarePainter

    override fun initProperties(typedArray: TypedArray) {
        circlePainter.initProperties(typedArray)
    }

    override fun <T> updateProperty(property: Property<T>) {
        circlePainter.updateProperty(property)
    }

    override fun draw(canvas: Canvas, rect: Rect) {
        currentPainter.draw(canvas, rect)
    }
}