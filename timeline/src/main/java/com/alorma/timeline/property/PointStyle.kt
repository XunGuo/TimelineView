package com.alorma.timeline.property

sealed class PointStyle : Property<Any>(Any()) {
    object CIRCLE : PointStyle()
    object SQUARE : PointStyle()
}