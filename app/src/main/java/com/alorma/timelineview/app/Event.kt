package com.alorma.timelineview.app

data class Event @JvmOverloads constructor(
        val name: String,
        val type: Int = 0,
        val alignment: Int = 1
)