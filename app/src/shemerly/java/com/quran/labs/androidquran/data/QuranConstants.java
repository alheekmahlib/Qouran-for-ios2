package com.bedifferent2.alquraanalkareem.android.data;

import com.bedifferent2.alquraanalkareem.android.util.QuranScreenInfo;
import com.bedifferent2.alquraanalkareem.android.util.ShemerlyPageProvider;

import android.support.annotation.NonNull;
import android.view.Display;

public class QuranConstants {
  public static final int NUMBER_OF_PAGES = 521;

  public static QuranScreenInfo.PageProvider getPageProvider(@NonNull Display display) {
    return new ShemerlyPageProvider();
  }
}
