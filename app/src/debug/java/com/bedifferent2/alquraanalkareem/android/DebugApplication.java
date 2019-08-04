package com.bedifferent2.alquraanalkareem.android;

import com.facebook.stetho.Stetho;
import com.bedifferent2.alquraanalkareem.android.component.application.ApplicationComponent;
import com.bedifferent2.alquraanalkareem.android.component.application.DaggerDebugApplicationComponent;
import com.bedifferent2.alquraanalkareem.android.module.application.ApplicationModule;
import com.squareup.leakcanary.LeakCanary;

import timber.log.Timber;

public class DebugApplication extends QuranApplication {

  @Override
  public void onCreate() {
    super.onCreate();

    if (!LeakCanary.isInAnalyzerProcess(this)) {
      Timber.plant(new Timber.DebugTree());
      Stetho.initializeWithDefaults(this);
      LeakCanary.install(this);
    }
  }

  @Override
  protected ApplicationComponent initializeInjector() {
    return DaggerDebugApplicationComponent.builder()
        .applicationModule(new ApplicationModule(this))
        .build();
  }
}
