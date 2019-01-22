package com.alorma.timeline

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import com.alorma.timeline.painter.line.LinePainter
import com.alorma.timeline.painter.Painter
import com.alorma.timeline.property.LineColor
import com.alorma.timeline.property.LineStyle

class TimelineView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val linePainter: Painter = LinePainter(context)

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
            linePainter.initProperties(typedArray)
        }
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.let {
            it.drawColor(Color.TRANSPARENT, PorterDuff.Mode.ADD)
            it.getClipBounds(drawRect)
            linePainter.draw(it, drawRect)
        }
    }

    fun setLineStyle(lineStyle: LineStyle) {
        linePainter.updateProperty(lineStyle)
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
}