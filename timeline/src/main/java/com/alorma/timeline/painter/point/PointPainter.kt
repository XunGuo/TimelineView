package com.alorma.timeline.painter.point

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Rect
import com.alorma.timeline.R
import com.alorma.timeline.painter.Painter
import com.alorma.timeline.property.PointStyle
import com.alorma.timeline.property.Property

class PointPainter(context: Context) : Painter {

    private val circlePainter: PointStylePainter = CirclePointStylePainter()
    private val squarePainter: PointStylePainter = SquarePointStylePainter()
    private var currentPainter: PointStylePainter = squarePainter

    private var strokeColor: Int = Color.WHITE
    private var fillColor: Int = Color.GRAY

    private var fillSize: Float = context.resources.getDimension(R.dimen.default_pointFillSize)
    private var strokeSize: Float = context.resources.getDimension(R.dimen.default_pointStrokeSize)

    override fun initProperties(typedArray: TypedArray) {
        circlePainter.initProperties(typedArray)
        squarePainter.initProperties(typedArray)

        val style = typedArray.getInt(R.styleable.TimelineView_timeline_pointStyle, CIRCLE)
        currentPainter = getPointStylePainter(style)

        loadColors(typedArray)
        loadSizes(typedArray)
    }

    private fun loadColors(typedArray: TypedArray) {
        strokeColor = typedArray.getColor(R.styleable.TimelineView_timeline_pointBorderColor,
                strokeColor)
        fillColor = typedArray.getColor(R.styleable.TimelineView_timeline_pointFillColor,
                fillColor)

        circlePainter.initColors(strokeColor, fillColor)
        squarePainter.initColors(strokeColor, fillColor)
    }

    private fun loadSizes(typedArray: TypedArray) {
        fillSize = typedArray.getDimension(R.styleable.TimelineView_timeline_pointFillSize,
                fillSize)
        strokeSize = typedArray.getDimension(R.styleable.TimelineView_timeline_pointStrokeSize,
                strokeSize)

        circlePainter.initSizes(strokeSize, fillSize)
        squarePainter.initSizes(strokeSize, fillSize)
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