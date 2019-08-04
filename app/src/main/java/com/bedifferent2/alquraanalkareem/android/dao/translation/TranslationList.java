package com.bedifferent2.alquraanalkareem.android.dao.translation;

import com.squareup.moshi.Json;

import java.util.List;

public class TranslationList {
  public final List<Translation> data;

  public TranslationList(List<Translation> translations) {
    this.data = translations;
  }
}
