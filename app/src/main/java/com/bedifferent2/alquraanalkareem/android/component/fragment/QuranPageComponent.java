package com.bedifferent2.alquraanalkareem.android.component.fragment;

import com.bedifferent2.alquraanalkareem.android.di.QuranPageScope;
import com.bedifferent2.alquraanalkareem.android.module.fragment.QuranPageModule;
import com.bedifferent2.alquraanalkareem.android.ui.fragment.QuranPageFragment;
import com.bedifferent2.alquraanalkareem.android.ui.fragment.TabletFragment;
import com.bedifferent2.alquraanalkareem.android.ui.fragment.TranslationFragment;

import dagger.Subcomponent;

@QuranPageScope
@Subcomponent(modules = QuranPageModule.class)
public interface QuranPageComponent {
  void inject(QuranPageFragment quranPageFragment);
  void inject(TabletFragment tabletFragment);
  void inject(TranslationFragment translationFragment);

  @Subcomponent.Builder interface Builder {
    Builder withQuranPageModule(QuranPageModule quranPageModule);
    QuranPageComponent build();
  }
}
