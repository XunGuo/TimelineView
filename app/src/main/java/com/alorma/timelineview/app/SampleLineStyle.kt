package com.alorma.timelineview.app

sealed class SampleLineStyle {
    object LINE : SampleLineStyle()
    object DASHED : SampleLineStyle()
    object MIXED : SampleLineStyle()
}
sealed class SampleLineColor {
    object RED : SampleLineColor()
    object GREEN : SampleLineColor()
    object MIXED : SampleLineColor()
}