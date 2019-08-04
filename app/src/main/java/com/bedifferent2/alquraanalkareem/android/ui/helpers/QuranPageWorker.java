package com.bedifferent2.alquraanalkareem.android.ui.helpers;

import android.content.Context;

import com.bedifferent2.alquraanalkareem.android.common.Response;
import com.bedifferent2.alquraanalkareem.android.di.ActivityScope;
import com.bedifferent2.alquraanalkareem.android.util.QuranScreenInfo;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;

@ActivityScope
public class QuranPageWorker {
  private static final String TAG = "QuranPageWorker";

  private final Context appContext;
  private final OkHttpClient okHttpClient;
  private final String imageWidth;

  @Inject
  QuranPageWorker(Context context, OkHttpClient okHttpClient, String imageWidth) {
    this.appContext = context;
    this.okHttpClient = okHttpClient;
    this.imageWidth = imageWidth;
  }

  private Response downloadImage(int pageNumber) {
    Response response = null;
    OutOfMemoryError oom = null;

    try {
      response = QuranDisplayHelper.getQuranPage(okHttpClient, appContext, imageWidth, pageNumber);
    } catch (OutOfMemoryError me){

    }

    if (response == null ||
        (response.getBitmap() == null &&
            response.getErrorCode() != Response.ERROR_SD_CARD_NOT_FOUND)){
      if (QuranScreenInfo.getInstance().isDualPageMode(appContext)){
        String param = QuranScreenInfo.getInstance().getWidthParam();
        if (param.equals(imageWidth)){
          param = QuranScreenInfo.getInstance().getTabletWidthParam();
        }
        response = QuranDisplayHelper.getQuranPage(okHttpClient, appContext, param, pageNumber);
        if (response.getBitmap() == null){
        }
      }

    }

    if ((response == null || response.getBitmap() == null) && oom != null) {
      throw oom;
    }

    response.setPageData(pageNumber);
    return response;
  }

  public Observable<Response> loadPages(Integer... pages) {
    return Observable.fromArray(pages)
        .flatMap(page -> Observable.fromCallable(() -> downloadImage(page)))
        .subscribeOn(Schedulers.io());
  }
}
