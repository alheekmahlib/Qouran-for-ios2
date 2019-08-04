package com.bedifferent2.alquraanalkareem.android.presenter.translation;

import android.support.annotation.NonNull;

import com.bedifferent2.alquraanalkareem.android.R;
import com.bedifferent2.alquraanalkareem.android.common.QuranAyahInfo;
import com.bedifferent2.alquraanalkareem.android.data.QuranInfo;
import com.bedifferent2.alquraanalkareem.android.data.SuraAyah;
import com.bedifferent2.alquraanalkareem.android.database.TranslationsDBAdapter;
import com.bedifferent2.alquraanalkareem.android.di.QuranPageScope;
import com.bedifferent2.alquraanalkareem.android.model.translation.TranslationModel;
import com.bedifferent2.alquraanalkareem.android.ui.PagerActivity;
import com.bedifferent2.alquraanalkareem.android.util.QuranSettings;
import com.bedifferent2.alquraanalkareem.android.util.ShareUtil;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;

@QuranPageScope
public class TranslationPresenter extends
    BaseTranslationPresenter<TranslationPresenter.TranslationScreen> {
  private final Integer[] pages;
  private final QuranSettings quranSettings;

  @Inject
  TranslationPresenter(TranslationModel translationModel,
                       QuranSettings quranSettings,
                       TranslationsDBAdapter translationsAdapter,
                       Integer... pages) {
    super(translationModel, translationsAdapter);
    this.pages = pages;
    this.quranSettings = quranSettings;
  }

  public void refresh() {
    if (disposable != null) {
      disposable.dispose();
    }

    disposable = Observable.fromArray(pages)
        .flatMap(page -> getVerses(quranSettings.wantArabicInTranslationView(),
            getTranslations(quranSettings), QuranInfo.getVerseRangeForPage(page))
            .toObservable())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeWith(new DisposableObserver<ResultHolder>() {
          @Override
          public void onNext(ResultHolder result) {
            if (translationScreen != null && result.ayahInformation.size() > 0) {
              translationScreen.setVerses(
                  getPage(result.ayahInformation), result.translations, result.ayahInformation);
              translationScreen.updateScrollPosition();
            }
          }

          @Override
          public void onError(Throwable e) {
          }

          @Override
          public void onComplete() {
          }
        });
  }

  public void onTranslationAction(PagerActivity activity,
                                  QuranAyahInfo ayah,
                                  String[] translationNames,
                                  int actionId) {
    switch (actionId) {
      case R.id.cab_share_ayah_link: {
        SuraAyah bounds = new SuraAyah(ayah.sura, ayah.ayah);
        activity.shareAyahLink(bounds, bounds);
        break;
      }
      case R.id.cab_share_ayah_text:
      case R.id.cab_copy_ayah: {
        String shareText = ShareUtil.getShareText(activity, ayah, translationNames);
        if (actionId == R.id.cab_share_ayah_text) {
          ShareUtil.shareViaIntent(activity, shareText, R.string.share_ayah_text);
        } else {
          ShareUtil.copyToClipboard(activity, shareText);
        }
        break;
      }
    }
  }

  private int getPage(List<QuranAyahInfo> result) {
    final int page;
    if (pages.length == 1) {
      page = pages[0];
    } else {
      QuranAyahInfo ayahInfo = result.get(0);
      page = QuranInfo.getPageFromSuraAyah(ayahInfo.sura, ayahInfo.ayah);
    }
    return page;
  }

  public interface TranslationScreen {
    void setVerses(int page, @NonNull String[] translations, @NonNull List<QuranAyahInfo> verses);
    void updateScrollPosition();
  }
}
