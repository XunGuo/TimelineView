package com.alorma.timeline.painter

import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Rect
import com.alorma.timeline.property.Property

interface Painter {
    fun initProperties(typedArray: TypedArray)
    fun <T> updateProperty(property: Property<T>)
    fun draw(canvas: Canvas, rect: Rect)


}