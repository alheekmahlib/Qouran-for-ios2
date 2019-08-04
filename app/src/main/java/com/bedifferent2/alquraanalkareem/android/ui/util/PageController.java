package com.bedifferent2.alquraanalkareem.android.ui.util;

import android.view.MotionEvent;

import com.bedifferent2.alquraanalkareem.android.ui.helpers.AyahSelectedListener;

public interface PageController {
  boolean handleTouchEvent(MotionEvent event, AyahSelectedListener.EventType eventType, int page);
  void handleRetryClicked();
  void onScrollChanged(int x, int y, int oldx, int oldy);
}
