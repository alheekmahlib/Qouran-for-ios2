package com.bedifferent2.alquraanalkareem.android.model.translation;

import android.content.Context;

import com.bedifferent2.alquraanalkareem.android.common.QuranText;
import com.bedifferent2.alquraanalkareem.android.data.QuranDataProvider;
import com.bedifferent2.alquraanalkareem.android.data.VerseRange;
import com.bedifferent2.alquraanalkareem.android.database.DatabaseHandler;
import com.bedifferent2.alquraanalkareem.android.di.ActivityScope;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

@ActivityScope
public class TranslationModel {
  private Context appContext;

  @Inject
  TranslationModel(Context appContext) {
    this.appContext = appContext;
  }

  public Single<List<QuranText>> getArabicFromDatabase(VerseRange verses) {
    return getVersesFromDatabase(verses,
        QuranDataProvider.QURAN_ARABIC_DATABASE, DatabaseHandler.TextType.ARABIC);
  }

  public Single<List<QuranText>> getTranslationFromDatabase(VerseRange verses, String db) {
    return getVersesFromDatabase(verses, db, DatabaseHandler.TextType.TRANSLATION);
  }

  private Single<List<QuranText>> getVersesFromDatabase(VerseRange verses,
                                                        String database,
                                                        @DatabaseHandler.TextType int type) {
    return Single.fromCallable(() -> {
      DatabaseHandler databaseHandler = DatabaseHandler.getDatabaseHandler(appContext, database);
      return databaseHandler.getVerses(verses, type);
    });
  }
}
