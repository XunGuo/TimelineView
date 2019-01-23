package com.alorma.timeline.property

sealed class TimelinePositionOption(position: Int) : Property<Int>(position) {
    object POSITION_CENTER : TimelinePositionOption(1)
    object POSITION_CENTER_VERTICAL : TimelinePositionOption(2)
    object POSITION_CENTER_HORIZONTAL : TimelinePositionOption(4)
    object POSITION_TOP : TimelinePositionOption(8)
    object POSITION_BOTTOM : TimelinePositionOption(16)
    object POSITION_START : TimelinePositionOption(32)
    object POSITION_END : TimelinePositionOption(64)
}

class TimelinePosition(positions: List<TimelinePositionOption>) :
        Property<List<TimelinePositionOption>>(positions)