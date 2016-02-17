TimelineView
============

This library provide android `View` to add Timeline to your application.

![image](screenshot.png)

## Download

Via gradle:

```groovy
dependencies {
	compile 'com.github.alorma:timelineview:2.2.0'
}
```

## Usage

Add View to your layout

```xml

<Layout 
    xmlns:app="http://schemas.android.com/apk/res-auto">
...
<com.alorma.timeline.RoundTimelineView
	android:id="@+id/timeline1"
   	android:layout_width="?android:listPreferredItemHeight"
   	android:layout_height="?android:listPreferredItemHeight"
   	app:timeline_indicatorSize="20dp"
   	app:timeline_lineStyle="linear"
   	app:timeline_type="middle"/>
...
</Layout>
```

## Custom attributes

### Line

| Attr name | Attr format | Example |
|---|---|---|
| timeline_lineWidth | dimension | 20dp |
| timeline_lineColor | color | @color/red / #FF0000 |
| timeline_lineStyle | enum | dashed / linear |
| timeline_type | enum | hidden / start / middle / line / end |

### Indicator


| Attr name | Attr format | Example |
|---|---|---|
| timeline_indicatorSize | dimension | 20dp |
| timeline_indicatorColor | color | @color/red / #FF0000 |
| timeline_alignment | enum | start / middle  / end |
| timeline_drawInternal | boolean | true / false |
| timeline_internalColor | color | @color/red / #FF0000 |
| timeline_internalDrawable | reference | @drawable / @color |
| timeline_internalPadding | dimension | 20dp |


### License
TimelineView by *Bernat Borras* is licensed under a [Apache License 2.0](http://www.apache.org/licenses/LICENSE-2.0).