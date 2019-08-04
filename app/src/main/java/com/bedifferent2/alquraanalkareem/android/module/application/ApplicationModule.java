package com.bedifferent2.alquraanalkareem.android.module.application;

import android.app.Application;
import android.content.Context;

import com.bedifferent2.alquraanalkareem.android.util.QuranScreenInfo;
import com.bedifferent2.alquraanalkareem.android.util.QuranSettings;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {
  private final Application application;

  public ApplicationModule(Application application) {
    this.application = application;
  }

  @Provides
  Context provideApplicationContext() {
    return this.application;
  }

  @Provides
  @Singleton
  QuranSettings provideQuranSettings() {
    return QuranSettings.getInstance(application);
  }

  @Provides
  @Singleton
  QuranScreenInfo provideQuranScreenInfo() {
    return QuranScreenInfo.getOrMakeInstance(application);
  }
}
