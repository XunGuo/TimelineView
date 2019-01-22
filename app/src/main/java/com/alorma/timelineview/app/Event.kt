package com.alorma.timelineview.app

data class Event @JvmOverloads constructor(
        val name: String,
        val lineVPosition: SampleLineVPosition = SampleLineVPosition.FULL,
        val lineHPosition: SampleLineHPosition = SampleLineHPosition.CENTER,
        val lineWidth: Float? = null
)