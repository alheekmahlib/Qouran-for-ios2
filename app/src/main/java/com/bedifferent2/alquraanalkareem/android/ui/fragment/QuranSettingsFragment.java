package com.bedifferent2.alquraanalkareem.android.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;

import com.bedifferent2.alquraanalkareem.android.QuranAdvancedPreferenceActivity;
import com.bedifferent2.alquraanalkareem.android.QuranApplication;
import com.bedifferent2.alquraanalkareem.android.QuranPreferenceActivity;
import com.bedifferent2.alquraanalkareem.android.R;
import com.bedifferent2.alquraanalkareem.android.data.Constants;
import com.bedifferent2.alquraanalkareem.android.model.bookmark.BookmarkImportExportModel;
import com.bedifferent2.alquraanalkareem.android.ui.AudioManagerActivity;
import com.bedifferent2.alquraanalkareem.android.ui.TranslationManagerActivity;

import javax.inject.Inject;

public class QuranSettingsFragment extends PreferenceFragment implements
    SharedPreferences.OnSharedPreferenceChangeListener {
  @Inject BookmarkImportExportModel bookmarkImportExportModel;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    addPreferencesFromResource(R.xml.quran_preferences);

    final Context context = getActivity();
    Context mAppContext = context.getApplicationContext();

    // field injection
    ((QuranApplication) mAppContext).getApplicationComponent().inject(this);

    // handle translation manager click
    final Preference translationPref = findPreference(Constants.PREF_TRANSLATION_MANAGER);
    translationPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
      @Override
      public boolean onPreferenceClick(Preference preference) {
        startActivity(new Intent(getActivity(), TranslationManagerActivity.class));
        return true;
      }
    });

    // handle audio manager click
    final Preference audioManagerPref = findPreference(Constants.PREF_AUDIO_MANAGER);
    audioManagerPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
      @Override
      public boolean onPreferenceClick(Preference preference) {
        startActivity(new Intent(getActivity(), AudioManagerActivity.class));
        return true;
      }
    });

  }

  @Override
  public void onResume() {
    super.onResume();
    getPreferenceScreen().getSharedPreferences()
        .registerOnSharedPreferenceChangeListener(this);
  }

  @Override
  public void onPause() {
    getPreferenceScreen().getSharedPreferences()
        .unregisterOnSharedPreferenceChangeListener(this);
    super.onPause();
  }

  @Override
  public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
                                        String key) {
    if (key.equals(Constants.PREF_USE_ARABIC_NAMES)) {
      final Context context = getActivity();
      if (context instanceof QuranPreferenceActivity) {
        ((QuranPreferenceActivity) context).restartActivity();
      }
    }
  }

  @Override
  public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
    final String key = preference.getKey();
    if ("key_prefs_advanced".equals(key)) {
      Intent intent = new Intent(getActivity(), QuranAdvancedPreferenceActivity.class);
      startActivity(intent);
      return true;
    }

    return super.onPreferenceTreeClick(preferenceScreen, preference);
  }
}
