package com.bedifferent2.alquraanalkareem.android.presenter.quran;

import android.graphics.Bitmap;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

import com.bedifferent2.alquraanalkareem.android.common.AyahBounds;
import com.bedifferent2.alquraanalkareem.android.dao.Bookmark;

import java.util.List;
import java.util.Map;

public interface QuranPageScreen {
  void setBookmarksOnPage(List<Bookmark> bookmarks);
  void setPageCoordinates(int page, RectF pageCoordinates);
  void setAyahCoordinatesError();
  void setPageBitmap(int page, @NonNull Bitmap pageBitmap);
  void hidePageDownloadError();
  void setPageDownloadError(@StringRes int errorMessage);
  void setAyahCoordinatesData(int page, Map<String, List<AyahBounds>> coordinates);
}
