package com.alorma.timeline.painter.line

import android.content.Context
import android.graphics.DashPathEffect
import android.graphics.Paint

class DashedLinePainter(context: Context) : LineStylePainter(context) {

    override fun paintLineStyle(paint: Paint) {
        paint.apply {
            val dashIntervals = floatArrayOf(25f, 20f)
            val dashPathEffect = DashPathEffect(dashIntervals, 1f)
            pathEffect = dashPathEffect
        }
    }
}