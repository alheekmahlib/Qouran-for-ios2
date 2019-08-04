package com.bedifferent2.alquraanalkareem.android.component.activity;

import com.bedifferent2.alquraanalkareem.android.component.fragment.QuranPageComponent;
import com.bedifferent2.alquraanalkareem.android.di.ActivityScope;
import com.bedifferent2.alquraanalkareem.android.module.activity.PagerActivityModule;
import com.bedifferent2.alquraanalkareem.android.ui.PagerActivity;
import com.bedifferent2.alquraanalkareem.android.ui.fragment.AyahTranslationFragment;

import dagger.Subcomponent;

@ActivityScope
@Subcomponent(modules = PagerActivityModule.class)
public interface PagerActivityComponent {
  // subcomponents
  QuranPageComponent.Builder quranPageComponentBuilder();

  void inject(PagerActivity pagerActivity);
  void inject(AyahTranslationFragment ayahTranslationFragment);

  @Subcomponent.Builder interface Builder {
    Builder withPagerActivityModule(PagerActivityModule pagerModule);
    PagerActivityComponent build();
  }
}
