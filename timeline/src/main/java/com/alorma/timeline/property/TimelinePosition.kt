package com.alorma.timeline.property

sealed class TimelinePosition(position: Int) : Property<Int>(position) {
    object POSITION_CENTER : TimelinePosition(1)
    object POSITION_CENTER_VERTICAL : TimelinePosition(2)
    object POSITION_CENTER_HORIZONTAL : TimelinePosition(4)
    object POSITION_TOP : TimelinePosition(8)
    object POSITION_BOTTOM : TimelinePosition(16)
    object POSITION_START : TimelinePosition(32)
    object POSITION_END : TimelinePosition(64)
}