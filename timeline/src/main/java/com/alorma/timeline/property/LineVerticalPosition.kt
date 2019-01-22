package com.alorma.timeline.property

sealed class LineVerticalPosition : Property<Any>(Any()) {
    object START : LineVerticalPosition()
    object END : LineVerticalPosition()
    object FULL : LineVerticalPosition()
}