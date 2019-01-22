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


sealed class SampleLinePosition {
    object START : SampleLinePosition()
    object END : SampleLinePosition()
    object FULL : SampleLinePosition()
}

sealed class SamplePointStyle {
    object CIRCLE : SamplePointStyle()
    object SQUARE : SamplePointStyle()
    object MIXED : SamplePointStyle()
}