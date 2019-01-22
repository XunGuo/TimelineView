package com.alorma.timeline.painter.point

import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Rect
import com.alorma.timeline.R
import com.alorma.timeline.painter.Painter
import com.alorma.timeline.property.PointStyle
import com.alorma.timeline.property.Property

class PointPainter : Painter {

    private val circlePainter: PointStylePainter = CirclePointStylePainter()
    private val squarePainter: PointStylePainter = SquarePointStylePainter()
    private var currentPainter: PointStylePainter = squarePainter

    override fun initProperties(typedArray: TypedArray) {
        circlePainter.initProperties(typedArray)
        squarePainter.initProperties(typedArray)

        val style = typedArray.getInt(R.styleable.TimelineView_timeline_pointStyle, CIRCLE)
        currentPainter = getPointStylePainter(style)
    }

    private fun getPointStylePainter(style: Int): PointStylePainter = when (style) {
        SQUARE -> squarePainter
        else -> circlePainter
    }

    override fun <T> updateProperty(property: Property<T>) {
        currentPainter = when (property) {
            is PointStyle.SQUARE -> squarePainter
            else -> circlePainter
        }
        circlePainter.updateProperty(property)
        squarePainter.updateProperty(property)
    }

    override fun draw(canvas: Canvas, rect: Rect) {
        currentPainter.draw(canvas, rect)
    }

    companion object {
        const val CIRCLE = -1
        const val SQUARE = 0
    }
}