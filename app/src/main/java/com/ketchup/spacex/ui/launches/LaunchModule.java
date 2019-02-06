package com.ketchup.spacex.ui.launches;

import com.ketchup.spacex.network.SpaceXService;

import dagger.Module;
import dagger.Provides;

@Module
public class LaunchModule {

    @Provides
    static LaunchesPresenter providePresenter(SpaceXService service) {
        return new LaunchesPresenter(service);
    }

}
