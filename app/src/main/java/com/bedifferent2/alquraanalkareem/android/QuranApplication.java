package com.bedifferent2.alquraanalkareem.android;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.support.annotation.NonNull;

import com.bedifferent2.alquraanalkareem.android.component.application.DaggerApplicationComponent;
import com.bedifferent2.alquraanalkareem.android.component.application.ApplicationComponent;
import com.bedifferent2.alquraanalkareem.android.module.application.ApplicationModule;
import com.bedifferent2.alquraanalkareem.android.util.QuranSettings;
import com.bedifferent2.alquraanalkareem.android.util.RecordingLogTree;

import java.util.Locale;

import timber.log.Timber;

public class QuranApplication extends Application {
  private ApplicationComponent applicationComponent;

  @Override
  public void onCreate() {
    super.onCreate();
    Timber.plant(new RecordingLogTree());
    this.applicationComponent = initializeInjector();
  }

  protected ApplicationComponent initializeInjector() {
    return DaggerApplicationComponent.builder()
        .applicationModule(new ApplicationModule(this))
        .build();
  }

  public ApplicationComponent getApplicationComponent() {
    return this.applicationComponent;
  }

  public void refreshLocale(@NonNull Context context, boolean force) {
    final String language = QuranSettings.getInstance(this).isArabicNames() ? "ar" : null;

    final Locale locale;
    if ("ar".equals(language)) {
      locale = new Locale("ar");
    } else if (force) {
      // get the system locale (since we overwrote the default locale)
      locale = Resources.getSystem().getConfiguration().locale;
    } else {
      // nothing to do...
      return;
    }

    updateLocale(context, locale);
    final Context appContext = context.getApplicationContext();
    if (context != appContext) {
      updateLocale(appContext, locale);
    }
  }

  private void updateLocale(@NonNull Context context, @NonNull Locale locale) {
    final Resources resources = context.getResources();
    Configuration config = resources.getConfiguration();
    config.locale = locale;
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
      config.setLayoutDirection(config.locale);
    }
    resources.updateConfiguration(config, resources.getDisplayMetrics());
  }
}
