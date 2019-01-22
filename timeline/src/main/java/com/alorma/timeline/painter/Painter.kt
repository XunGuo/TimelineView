package com.alorma.timeline.painter

import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Rect

interface Painter {
    fun initProperties(typedArray: TypedArray)
    fun draw(canvas: Canvas, rect: Rect)
}