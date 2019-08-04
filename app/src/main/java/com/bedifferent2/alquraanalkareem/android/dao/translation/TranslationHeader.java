package com.bedifferent2.alquraanalkareem.android.dao.translation;

public class TranslationHeader implements TranslationRowData {
  public final String name;

  public TranslationHeader(String name) {
    this.name = name;
  }

  @Override
  public String name() {
    return this.name;
  }

  @Override
  public boolean isSeparator() {
    return true;
  }

  @Override
  public boolean needsUpgrade() {
    return false;
  }
}
