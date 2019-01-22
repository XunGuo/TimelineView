package com.alorma.timelineview.app

data class Event @JvmOverloads constructor(
        val name: String,
        val linePosition: SampleLinePosition = SampleLinePosition.FULL
)