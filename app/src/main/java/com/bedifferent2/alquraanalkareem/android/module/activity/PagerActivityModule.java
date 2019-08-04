package com.bedifferent2.alquraanalkareem.android.module.activity;

import com.bedifferent2.alquraanalkareem.android.di.ActivityScope;
import com.bedifferent2.alquraanalkareem.android.ui.PagerActivity;
import com.bedifferent2.alquraanalkareem.android.ui.helpers.AyahSelectedListener;
import com.bedifferent2.alquraanalkareem.android.util.QuranScreenInfo;
import com.bedifferent2.alquraanalkareem.android.util.QuranUtils;

import dagger.Module;
import dagger.Provides;

@Module
public class PagerActivityModule {
  private final PagerActivity pagerActivity;

  public PagerActivityModule(PagerActivity pagerActivity) {
    this.pagerActivity = pagerActivity;
  }

  @Provides
  AyahSelectedListener provideAyahSelectedListener() {
    return this.pagerActivity;
  }

  @Provides
  @ActivityScope
  String provideImageWidth(QuranScreenInfo screenInfo) {
    return QuranUtils.isDualPages(pagerActivity, screenInfo) ?
        screenInfo.getTabletWidthParam() : screenInfo.getWidthParam();
  }
}
