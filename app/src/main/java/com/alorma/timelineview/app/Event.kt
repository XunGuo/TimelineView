package com.alorma.timelineview.app

import com.alorma.timeline.TimelineView

data class Event @JvmOverloads constructor(
        val name: String,
        val type: Int = TimelineView.TYPE_DEFAULT,
        val alignment: Int = TimelineView.ALIGNMENT_DEFAULT
)