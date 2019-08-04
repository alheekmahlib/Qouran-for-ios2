package com.bedifferent2.alquraanalkareem.android.data;

import android.content.Context;
import android.support.annotation.Nullable;

import com.bedifferent2.alquraanalkareem.android.di.ActivityScope;
import com.bedifferent2.alquraanalkareem.android.util.QuranFileUtils;

import javax.inject.Inject;

@ActivityScope
public class AyahInfoDatabaseProvider {
  private final Context context;
  private final String widthParameter;
  @Nullable private AyahInfoDatabaseHandler databaseHandler;

  @Inject
  AyahInfoDatabaseProvider(Context context, String widthParameter) {
    this.context = context;
    this.widthParameter = widthParameter;
  }

  @Nullable
  public AyahInfoDatabaseHandler getAyahInfoHandler() {
    if (databaseHandler == null) {
      String filename = QuranFileUtils.getAyaPositionFileName(widthParameter);
      databaseHandler = AyahInfoDatabaseHandler.getAyahInfoDatabaseHandler(context, filename);
    }
    return databaseHandler;
  }
}
