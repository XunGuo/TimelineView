package com.alorma.timeline

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import com.alorma.timeline.painter.Painter
import com.alorma.timeline.painter.line.LinePainter
import com.alorma.timeline.painter.point.PointPainter
import com.alorma.timeline.property.*

class TimelineView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr), Painter {

    private val linePainter: Painter = LinePainter(context)
    private val pointPainter: Painter = PointPainter()

    private val drawRect: Rect = Rect()

    init {
        readAttrs(attrs, defStyleAttr)
    }

    private fun readAttrs(attrs: AttributeSet?, defStyleAttr: Int) {
        attrs?.let {
            val typedArray = context.theme.obtainStyledAttributes(
                    attrs,
                    R.styleable.TimelineView,
                    defStyleAttr,
                    0)
            initProperties(typedArray)
        }
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.let {
            it.drawColor(Color.TRANSPARENT, PorterDuff.Mode.ADD)
            it.getClipBounds(drawRect)
            draw(it, drawRect)
        }
    }

    override fun initProperties(typedArray: TypedArray) {
        linePainter.initProperties(typedArray)
        pointPainter.initProperties(typedArray)
    }

    override fun <T> updateProperty(property: Property<T>) {
        linePainter.updateProperty(property)
        pointPainter.updateProperty(property)
        invalidate()
    }

    override fun draw(canvas: Canvas, rect: Rect) {
        linePainter.draw(canvas, drawRect)
        pointPainter.draw(canvas, drawRect)
    }

    fun setLineStyle(lineStyle: LineStyle) {
        linePainter.updateProperty(lineStyle)
        invalidate()
    }

    fun setPointStyle(pointStyle: PointStyle) {
        pointPainter.updateProperty(pointStyle)
        invalidate()
    }

    fun setLineColor(lineColor: Int) {
        linePainter.updateProperty(LineColor(lineColor))
        invalidate()
    }

    fun setLineColor(lineColor: LineColor) {
        linePainter.updateProperty(lineColor)
        invalidate()
    }

    fun setLineVerticalPosition(property: LineVerticalPosition) {
        linePainter.updateProperty(property)
        invalidate()
    }
}