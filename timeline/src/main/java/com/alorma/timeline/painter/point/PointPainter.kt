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
import com.alorma.timeline.property.TimelinePosition
import com.alorma.timeline.property.TimelinePositionOption

class PointPainter(context: Context) : Painter {

    private val nonePainter: PointStylePainter = NonePointStylePainter()
    private val circlePainter: PointStylePainter = CirclePointStylePainter()
    private val squarePainter: PointStylePainter = SquarePointStylePainter()
    private var currentPainter: PointStylePainter = squarePainter

    private var strokeColor: Int = Color.WHITE
    private var fillColor: Int = Color.GRAY

    private var fillSize: Float = context.resources.getDimension(R.dimen.default_pointFillSize)
    private var strokeSize: Float = context.resources.getDimension(R.dimen.default_pointStrokeSize)

    private var validVerticalPositions: List<TimelinePositionOption> = listOf(
            TimelinePositionOption.POSITION_TOP,
            TimelinePositionOption.POSITION_CENTER,
            TimelinePositionOption.POSITION_CENTER_VERTICAL,
            TimelinePositionOption.POSITION_BOTTOM
    )

    private var validHorizontalPositions: List<TimelinePositionOption> = listOf(
            TimelinePositionOption.POSITION_START,
            TimelinePositionOption.POSITION_CENTER,
            TimelinePositionOption.POSITION_CENTER_HORIZONTAL,
            TimelinePositionOption.POSITION_END
    )
    private var verticalPosition: TimelinePositionOption = TimelinePositionOption.POSITION_CENTER_VERTICAL
    private var horizontalPosition: TimelinePositionOption = TimelinePositionOption.POSITION_CENTER_HORIZONTAL

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
        CIRCLE -> circlePainter
        else -> nonePainter
    }

    override fun <T> updateProperty(property: Property<T>) {
        when (property) {
            is TimelinePosition -> {
                verticalPosition = property.value.firstOrNull {
                    it in validVerticalPositions
                } ?: verticalPosition

                if (verticalPosition is TimelinePositionOption.POSITION_CENTER) {
                    verticalPosition = TimelinePositionOption.POSITION_CENTER_VERTICAL
                }

                horizontalPosition = property.value.firstOrNull {
                    it in validHorizontalPositions
                } ?: horizontalPosition

                if (horizontalPosition is TimelinePositionOption.POSITION_CENTER) {
                    horizontalPosition = TimelinePositionOption.POSITION_CENTER_HORIZONTAL
                }
            }
            is PointStyle -> {
                currentPainter = when (property) {
                    is PointStyle.SQUARE -> squarePainter
                    is PointStyle.CIRCLE -> circlePainter
                    else -> nonePainter
                }
            }
        }
        circlePainter.updateProperty(property)
        squarePainter.updateProperty(property)
    }

    override fun draw(canvas: Canvas, rect: Rect) {
        val vRect = when (verticalPosition) {
            TimelinePositionOption.POSITION_TOP ->
                Rect(rect.left, rect.top, rect.right, rect.top)
            TimelinePositionOption.POSITION_BOTTOM ->
                Rect(rect.left, rect.bottom, rect.right, rect.bottom)
            else ->
                rect
        }

        val hRect = when (horizontalPosition) {
            TimelinePositionOption.POSITION_START ->
                Rect(rect.left, vRect.top, rect.left, vRect.bottom)
            TimelinePositionOption.POSITION_END ->
                Rect(rect.right, vRect.top, rect.right, vRect.bottom)
            else ->
                rect
        }

        val useRect = Rect(hRect.left, vRect.top, hRect.right, vRect.bottom)

        currentPainter.draw(canvas, useRect)
    }

    companion object {
        const val CIRCLE = -1
        const val SQUARE = 0
    }
}