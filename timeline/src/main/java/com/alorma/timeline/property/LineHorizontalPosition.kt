package com.alorma.timeline.property

sealed class LineHorizontalPosition : Property<Any>(Any()) {
    object START : LineHorizontalPosition()
    object END : LineHorizontalPosition()
    object CENTER : LineHorizontalPosition()
}