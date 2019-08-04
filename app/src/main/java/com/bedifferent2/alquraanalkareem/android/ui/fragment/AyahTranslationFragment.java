package com.bedifferent2.alquraanalkareem.android.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.bedifferent2.alquraanalkareem.android.R;
import com.bedifferent2.alquraanalkareem.android.common.LocalTranslation;
import com.bedifferent2.alquraanalkareem.android.common.QuranAyahInfo;
import com.bedifferent2.alquraanalkareem.android.data.VerseRange;
import com.bedifferent2.alquraanalkareem.android.presenter.translation.InlineTranslationPresenter;
import com.bedifferent2.alquraanalkareem.android.ui.PagerActivity;
import com.bedifferent2.alquraanalkareem.android.ui.util.TranslationsSpinnerAdapter;
import com.bedifferent2.alquraanalkareem.android.util.QuranSettings;
import com.bedifferent2.alquraanalkareem.android.widgets.InlineTranslationView;
import com.bedifferent2.alquraanalkareem.android.widgets.QuranSpinner;

import java.util.List;
import java.util.Set;

import javax.inject.Inject;

public class AyahTranslationFragment extends AyahActionFragment
    implements InlineTranslationPresenter.TranslationScreen {

  private ProgressBar progressBar;
  private InlineTranslationView translationView;
  private View emptyState;
  private View translationControls;
  private QuranSpinner translator;
  private TranslationsSpinnerAdapter translationAdapter;
  private List<LocalTranslation> translations;

  @Inject QuranSettings quranSettings;
  @Inject InlineTranslationPresenter translationPresenter;

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    ((PagerActivity) getActivity()).getPagerActivityComponent().inject(this);
  }

  @Override
  public View onCreateView(LayoutInflater inflater,
                           ViewGroup container, Bundle savedInstanceState) {
    final View view = inflater.inflate(
        R.layout.translation_panel, container, false);

    translator = (QuranSpinner) view.findViewById(R.id.translator);
    translationView = (InlineTranslationView) view.findViewById(R.id.translation_view);
    progressBar = (ProgressBar) view.findViewById(R.id.progress);
    emptyState = view.findViewById(R.id.empty_state);
    translationControls = view.findViewById(R.id.controls);
    final View next = translationControls.findViewById(R.id.next_ayah);
    next.setOnClickListener(onClickListener);

    final View prev = translationControls.findViewById(R.id.previous_ayah);
    prev.setOnClickListener(onClickListener);

    final Button getTranslations =
        (Button) view.findViewById(R.id.get_translations_button);
    getTranslations.setOnClickListener(onClickListener);
    return view;
  }

  @Override
  public void onResume() {
    // currently needs to be before we call super.onResume
    translationPresenter.bind(this);
    super.onResume();
  }

  @Override
  public void onPause() {
    translationPresenter.unbind(this);
    super.onPause();
  }

  private View.OnClickListener onClickListener = v -> {
    final Activity activity = getActivity();
    if (activity instanceof PagerActivity) {
      final PagerActivity pagerActivity = (PagerActivity) activity;

      switch (v.getId()) {
        case R.id.get_translations_button:
          pagerActivity.startTranslationManager();
          break;
        case R.id.next_ayah:
          pagerActivity.nextAyah();
          break;
        case R.id.previous_ayah:
          pagerActivity.previousAyah();
          break;
      }
    }
  };

  @Override
  public void refreshView() {
    if (start == null || end == null) { return; }

    final Activity activity = getActivity();
    if (activity instanceof PagerActivity) {
      PagerActivity pagerActivity = (PagerActivity) activity;
      if (translations == null || translations.size() == 0) {
        translations = pagerActivity.getTranslations();
      }

      if (translations == null || translations.size() == 0) {
        progressBar.setVisibility(View.GONE);
        emptyState.setVisibility(View.VISIBLE);
        translationControls.setVisibility(View.GONE);
        return;
      }

      if (translationAdapter == null) {
        Set<String> activeTranslations = pagerActivity.getActiveTranslations();
        if (activeTranslations == null) {
          activeTranslations = quranSettings.getActiveTranslations();
        }

        translationAdapter = new TranslationsSpinnerAdapter(activity,
            R.layout.translation_ab_spinner_item,
            pagerActivity.getTranslationNames(),
            translations,
            activeTranslations,
            selectedItems -> {
              quranSettings.setActiveTranslations(selectedItems);
              refreshView();
            });
        translator.setAdapter(translationAdapter);
      }

      if (start.equals(end)) {
        translationControls.setVisibility(View.VISIBLE);
      } else {
        translationControls.setVisibility(View.GONE);
      }

      VerseRange verseRange = new VerseRange(start.sura, start.ayah, end.sura, end.ayah);
      translationPresenter.refresh(verseRange);
    }
  }

  @Override
  public void setVerses(@NonNull String[] translations, @NonNull List<QuranAyahInfo> verses) {
    progressBar.setVisibility(View.GONE);
    if (verses.size() > 0) {
      emptyState.setVisibility(View.GONE);
      translationView.setAyahs(translations, verses);
    } else {
      emptyState.setVisibility(View.VISIBLE);
    }
  }
}
