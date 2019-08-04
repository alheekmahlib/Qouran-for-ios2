package com.bedifferent2.alquraanalkareem.android.component.application;

import com.bedifferent2.alquraanalkareem.android.module.application.ApplicationModule;
import com.bedifferent2.alquraanalkareem.android.module.application.DatabaseModule;
import com.bedifferent2.alquraanalkareem.android.module.application.DebugNetworkModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = { ApplicationModule.class, DatabaseModule.class, DebugNetworkModule.class } )
interface DebugApplicationComponent extends ApplicationComponent {
}
