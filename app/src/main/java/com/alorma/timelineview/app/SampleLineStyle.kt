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

sealed class SampleLineVPosition {
    object START : SampleLineVPosition()
    object END : SampleLineVPosition()
    object FULL : SampleLineVPosition()
}

sealed class SampleLineHPosition {
    object START : SampleLineHPosition()
    object END : SampleLineHPosition()
    object CENTER : SampleLineHPosition()
}

sealed class SamplePointStyle {
    object CIRCLE : SamplePointStyle()
    object SQUARE : SamplePointStyle()
    object MIXED : SamplePointStyle()
}