package com.ketchup.spacex.ui.launches;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.ketchup.spacex.BuildConfig;
import com.ketchup.spacex.R;
import com.ketchup.spacex.core.BaseActivity;
import com.ketchup.spacex.data.Launch;
import com.ketchup.spacex.network.SpaceXService;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LaunchesActivity extends BaseActivity implements LaunchesContract.View,
        LaunchesAdapter.OnLaunchClickListener,
        LaunchesAdapter.OnLoadMoreListener {

    @Inject
    Context context;

    @Inject
    SpaceXService service;

    @Inject
    LaunchesPresenter presenter;

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private List<Launch> launches = new ArrayList<>();
    private LaunchesAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launches);
        ButterKnife.bind(this);
        presenter.takeView(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        adapter = new LaunchesAdapter(recyclerView, launches, this);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
        presenter.fetchLaunches();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.dropView();
    }

    //region LaunchesContract.View

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showLaunches(List<Launch> launches, boolean firstPage) {

        adapter.setLoading(false);
        adapter.setFinish(launches.size() < BuildConfig.PAGE_SIZE);

        if (firstPage) {
            this.launches.clear();
        }else {
            this.launches.remove(this.launches.size() - 1);
            adapter.notifyItemRemoved(this.launches.size());
        }

        this.launches.addAll(launches);
        adapter.notifyDataSetChanged();
    }

    //endregion

    //region LaunchesAdapter.OnLaunchClickListener

    @Override
    public void onLaunchClick(Launch launch) {
        Intent launchIntent = new Intent(this, LaunchActivity.class);
        launchIntent.putExtra(LaunchActivity.ARG_FLIGHT_NUMBER, launch.getFligthNumber());
        launchIntent.putExtra(LaunchActivity.ARG_MISSION_PATCH, launch.getLinks().getMissionPatch());
        startActivity(launchIntent);
    }

    //endregion

    //region LaunchesAdapter.OnLoadMoreListener

    @Override
    public void onLoadMore() {
        launches.add(null);
        recyclerView.post(new Runnable() {
            @Override
            public void run() {
                adapter.notifyItemInserted(launches.size() - 1);
            }
        });
        presenter.fetchMore();
    }

    //endregion


}
