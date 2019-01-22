package com.alorma.timeline.property;

sealed class LineStyle : Property<Any>(Any()) {
    object LINEAR : LineStyle()
    object DASHED : LineStyle()
}

data class LineColor(val lineColor: Int) : Property<Int>(lineColor)