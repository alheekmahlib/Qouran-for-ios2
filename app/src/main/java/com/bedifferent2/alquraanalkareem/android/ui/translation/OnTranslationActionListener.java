package com.bedifferent2.alquraanalkareem.android.ui.translation;

import com.bedifferent2.alquraanalkareem.android.common.QuranAyahInfo;

public interface OnTranslationActionListener {
  void onTranslationAction(QuranAyahInfo ayah, String[] translationNames, int actionId);
}
