package com.ketchup.spacex.di;

import com.ketchup.spacex.ui.launches.LaunchActivity;
import com.ketchup.spacex.ui.launches.LaunchModule;
import com.ketchup.spacex.ui.launches.LaunchesActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = LaunchModule.class)
    abstract LaunchesActivity bindLaunchesActivity();

    @ContributesAndroidInjector(modules = LaunchModule.class)
    abstract LaunchActivity bindLauncheActivity();
    
}
