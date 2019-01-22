package com.alorma.timeline

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import com.alorma.timeline.painter.LinePainter
import com.alorma.timeline.painter.Painter

class TimelineView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val painters: List<Painter> by lazy {
        listOf<Painter>(
                LinePainter(context)
        )
    }

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

            painters.forEach { painter -> painter.initProperties(typedArray) }
        }
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.let {
            it.getClipBounds(drawRect)
            painters.forEach { painter -> painter.draw(it, drawRect) }
        }
    }
}