package com.bedifferent2.alquraanalkareem.android.presenter.translation;

import android.support.annotation.NonNull;

import com.bedifferent2.alquraanalkareem.android.common.QuranAyahInfo;
import com.bedifferent2.alquraanalkareem.android.data.VerseRange;
import com.bedifferent2.alquraanalkareem.android.database.TranslationsDBAdapter;
import com.bedifferent2.alquraanalkareem.android.model.translation.TranslationModel;
import com.bedifferent2.alquraanalkareem.android.util.QuranSettings;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;

public class InlineTranslationPresenter extends
    BaseTranslationPresenter<InlineTranslationPresenter.TranslationScreen> {
  private final QuranSettings quranSettings;

  @Inject
  InlineTranslationPresenter(TranslationModel translationModel,
                             TranslationsDBAdapter dbAdapter,
                             QuranSettings quranSettings) {
    super(translationModel, dbAdapter);
    this.quranSettings = quranSettings;
  }

  public void refresh(VerseRange verseRange) {
    if (disposable != null) {
      disposable.dispose();
    }

    disposable = getVerses(false, getTranslations(quranSettings), verseRange)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeWith(new DisposableSingleObserver<ResultHolder>() {
          @Override
          public void onSuccess(ResultHolder result) {
            if (translationScreen != null) {
              translationScreen.setVerses(result.translations, result.ayahInformation);
            }
          }

          @Override
          public void onError(Throwable e) {
          }
        });
  }

  public interface TranslationScreen {
    void setVerses(@NonNull String[] translations, @NonNull List<QuranAyahInfo> verses);
  }
}
