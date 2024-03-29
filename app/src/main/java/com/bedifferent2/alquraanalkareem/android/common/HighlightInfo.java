package com.bedifferent2.alquraanalkareem.android.common;

import com.bedifferent2.alquraanalkareem.android.ui.helpers.HighlightType;

public class HighlightInfo {
  public final int sura;
  public final int ayah;
  public final HighlightType highlightType;
  public final boolean scrollToAyah;

  public HighlightInfo(int sura, int ayah, HighlightType type, boolean scrollToAyah) {
    this.sura = sura;
    this.ayah = ayah;
    this.highlightType = type;
    this.scrollToAyah = scrollToAyah;
  }
}
