package com.ketchup.spacex.di;

import com.google.gson.Gson;
import com.ketchup.spacex.BuildConfig;
import com.ketchup.spacex.network.SpaceXService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkModule {

    @Singleton
    @Provides
    SpaceXService provideSpaceXService() {
        Retrofit retrofit = new Retrofit.Builder()
                .client(new OkHttpClient())
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(SpaceXService.class);
    }

}
