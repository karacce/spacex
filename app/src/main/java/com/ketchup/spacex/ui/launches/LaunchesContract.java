package com.ketchup.spacex.ui.launches;

import com.ketchup.spacex.core.BasePresenter;
import com.ketchup.spacex.core.BaseView;
import com.ketchup.spacex.data.Launch;

import java.util.List;

public interface LaunchesContract {

    interface View extends BaseView<Presenter> {
        void showLoading();
        void hideLoading();
        void showLaunches(List<Launch> launches, boolean firstPage);
    }

    interface Presenter extends BasePresenter<View> {
        void fetchLaunches();
        void fetchMore();
    }

}
