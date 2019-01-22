package com.alorma.timelineview.app

sealed class SampleLineStyle {
    object LINE : SampleLineStyle()
    object DASHED : SampleLineStyle()
    object MIXED : SampleLineStyle()
}