package com.ketchup.spacex.ui.launches;

import com.ketchup.spacex.BuildConfig;
import com.ketchup.spacex.data.Launch;
import com.ketchup.spacex.network.SpaceXService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LaunchesPresenter implements LaunchesContract.Presenter {

    private LaunchesContract.View view;
    private SpaceXService service;
    private int page = 0;

    public LaunchesPresenter(SpaceXService service) {
        this.service = service;
    }

    @Override
    public void takeView(LaunchesContract.View view) {
        this.view = view;
    }

    @Override
    public void dropView() {
        this.view = null;
    }

    @Override
    public void fetchMore() {
        page++;
        fetchLaunches();
    }

    @Override
    public void fetchLaunches() {

        if (page == 0)
            view.showLoading();

        service.getLaunches(page * BuildConfig.PAGE_SIZE, BuildConfig.PAGE_SIZE)
                .enqueue(new Callback<List<Launch>>() {
                    @Override
                    public void onResponse(Call<List<Launch>> call, Response<List<Launch>> response) {
                        view.hideLoading();
                        if (response.isSuccessful()) {
                            view.showLaunches(response.body(), page == 0);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Launch>> call, Throwable t) {
                        view.hideLoading();
                    }
                });
    }
}
