package com.bedifferent2.alquraanalkareem.android.widgets;

import android.content.Context;

public class QuranTabletImagePageLayout extends QuranImagePageLayout {

  public QuranTabletImagePageLayout(Context context) {
    super(context);
  }

  @Override
  protected boolean shouldWrapWithScrollView() {
    return false;
  }
}
