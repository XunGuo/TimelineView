package com.alorma.timeline.property;

sealed class LineStyle : Property<Any>(Any()) {
    object LINEAR : LineStyle()
    object DASHED : LineStyle()
}