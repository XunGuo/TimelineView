TimelineView
============

This library provide android `View` to add Timeline to your application.

![image](screenshot.png)

## Download

Via gradle:

```groovy
dependencies {
	compile 'com.github.alorma:timelineview:2.3.0'
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
Or 
```xml
<Layout 
    xmlns:app="http://schemas.android.com/apk/res-auto">
...
<com.alorma.timeline.SquareTimelineView
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

### Contributors

<a href="https:/github.com/alorma"><img src="https://avatars3.githubusercontent.com/u/887462?v=3&s=460" alt="Bernat Borras" height="80" width="80" target="_blank"/></a> | <a href="https://github.com/hrules6872"><img src="https://avatars2.githubusercontent.com/u/5445152?v=3&s=400" alt="Héctor de Isidro" height="80" width="80" target="_blank"/></a> |
---|---|
[Bernat Borras](https://github.com/alorma) | [Héctor de Isidro](https://github.com/hrules6872) |

### Advanced - Custom shape

If you want to create a new shape for indicator (like a diamond), you can! Just extends `TimelineView` and implement the following  methods:

```java
protected abstract void drawIndicator(Canvas canvas, Paint paintStart, float centerX,
	float centerY, float size);
protected abstract void drawInternal(Canvas canvas, Paint paintInternal, float centerX,
	float centerY, float size);
protected abstract void drawBitmap(Canvas canvas, float left, float top, int size);
```

### License
TimelineView by *Bernat Borras* is licensed under a [Apache License 2.0](http://www.apache.org/licenses/LICENSE-2.0).
