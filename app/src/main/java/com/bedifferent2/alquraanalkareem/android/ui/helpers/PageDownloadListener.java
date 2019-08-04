package com.bedifferent2.alquraanalkareem.android.ui.helpers;

import android.graphics.drawable.BitmapDrawable;

import com.bedifferent2.alquraanalkareem.android.common.Response;

public interface PageDownloadListener {
  void onLoadImageResponse(BitmapDrawable drawable, Response response);
}
