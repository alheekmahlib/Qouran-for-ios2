package com.bedifferent2.alquraanalkareem.android.module.application;

import android.content.Context;

import com.bedifferent2.alquraanalkareem.android.database.BookmarksDBAdapter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DatabaseModule {

  @Provides
  @Singleton
  static BookmarksDBAdapter provideBookmarkDatabaseAdapter(Context context) {
    return new BookmarksDBAdapter(context);
  }
}
