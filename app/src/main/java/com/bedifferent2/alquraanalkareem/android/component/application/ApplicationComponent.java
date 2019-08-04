package com.bedifferent2.alquraanalkareem.android.component.application;

import com.bedifferent2.alquraanalkareem.android.QuranImportActivity;
import com.bedifferent2.alquraanalkareem.android.component.activity.PagerActivityComponent;
import com.bedifferent2.alquraanalkareem.android.data.QuranDataProvider;
import com.bedifferent2.alquraanalkareem.android.module.application.ApplicationModule;
import com.bedifferent2.alquraanalkareem.android.module.application.DatabaseModule;
import com.bedifferent2.alquraanalkareem.android.module.application.NetworkModule;
import com.bedifferent2.alquraanalkareem.android.service.QuranDownloadService;
import com.bedifferent2.alquraanalkareem.android.ui.QuranActivity;
import com.bedifferent2.alquraanalkareem.android.ui.TranslationManagerActivity;
import com.bedifferent2.alquraanalkareem.android.ui.fragment.AddTagDialog;
import com.bedifferent2.alquraanalkareem.android.ui.fragment.BookmarksFragment;
import com.bedifferent2.alquraanalkareem.android.ui.fragment.QuranAdvancedSettingsFragment;
import com.bedifferent2.alquraanalkareem.android.ui.fragment.QuranSettingsFragment;
import com.bedifferent2.alquraanalkareem.android.ui.fragment.TagBookmarkDialog;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = { ApplicationModule.class, DatabaseModule.class, NetworkModule.class } )
public interface ApplicationComponent {
  // subcomponents
  PagerActivityComponent.Builder pagerActivityComponentBuilder();

  // content provider
  void inject(QuranDataProvider quranDataProvider);

  // services
  void inject(QuranDownloadService quranDownloadService);

  // activities
  void inject(QuranActivity quranActivity);
  void inject(QuranImportActivity quranImportActivity);

  // fragments
  void inject(BookmarksFragment bookmarksFragment);
  void inject(QuranSettingsFragment fragment);
  void inject(TranslationManagerActivity translationManagerActivity);
  void inject(QuranAdvancedSettingsFragment quranAdvancedSettingsFragment);

  // dialogs
  void inject(TagBookmarkDialog tagBookmarkDialog);
  void inject(AddTagDialog addTagDialog);
}
