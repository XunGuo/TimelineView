/*
package com.alorma.timeline;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import android.util.AttributeSet;

public class RoundTimelineView extends TimelineView {
  public RoundTimelineView(Context context) {
    this(context, null);
  }

  public RoundTimelineView(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public RoundTimelineView(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
  }

  @Override
  public void drawIndicator(Canvas canvas, Paint paintStart, float centerX, float centerY,
      float size) {
    drawCircle(canvas, centerX, centerY, size, paintStart);
  }

  @Override
  protected void drawInternal(Canvas canvas, Paint paint, float centerX, float centerY,
      float size) {
    drawCircle(canvas, centerX, centerY, size, paint);
  }

  @Override
  protected void drawBitmap(Canvas canvas, float left, float top, int size) {
    if (bitmap != null) {
      if (internalBitmapCache == null) {
        internalBitmapCache = transform(bitmap, size);
      }
      if (internalBitmapCache != null) {
        canvas.drawBitmap(internalBitmapCache, left, top, null);
      }
    }
  }

  private void drawCircle(Canvas canvas, float centerX, float centerY, float radius,
      Paint paint) {
    if (canvas != null) {
      canvas.drawCircle(centerX, centerY, radius, paint);
    }
  }

  private Bitmap transform(Bitmap source, int size) {
    if (source != null) {
      RoundedBitmapDrawable drawable =
          RoundedBitmapDrawableFactory.create(getResources(), source);
      drawable.setCornerRadius(100);
      Bitmap.Config configure = source.getConfig() != null ? source.getConfig() : Bitmap.Config.ARGB_8888;
      Bitmap output = Bitmap.createBitmap(size, size, configure);
      Canvas canvas = new Canvas(output);
      drawable.setAntiAlias(true);
      drawable.setBounds(0, 0, size, size);
      drawable.draw(canvas);
      if (!source.equals(output)) {
        source.recycle();
      }
      return output;
    }
    return null;
  }
}
*/