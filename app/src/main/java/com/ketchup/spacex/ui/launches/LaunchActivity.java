package com.ketchup.spacex.ui.launches;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.ketchup.spacex.R;
import com.ketchup.spacex.core.BaseActivity;
import com.ketchup.spacex.data.Launch;
import com.ketchup.spacex.network.SpaceXService;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LaunchActivity extends BaseActivity {
    static final String ARG_FLIGHT_NUMBER = "com.ketchup.spacex.ui.launches.flight.number";
    static final String ARG_MISSION_PATCH = "com.ketchup.spacex.ui.launches.mission.patch";

    @Inject
    Context context;

    @Inject
    SpaceXService service;

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    @BindView(R.id.info_layout)
    View infoLayout;

    @BindView(R.id.mission_patch_image_view)
    AppCompatImageView missionPatch;

    @BindView(R.id.rocket_name_text_view)
    AppCompatTextView rocketName;

    @BindView(R.id.rocket_type_text_view)
    AppCompatTextView rocketType;

    @BindView(R.id.site_name_text_view)
    AppCompatTextView siteName;

    @BindView(R.id.details_text_view)
    AppCompatTextView details;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        ButterKnife.bind(this);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        Glide.with(context).load(getIntent().getStringExtra(ARG_MISSION_PATCH)).into(missionPatch);
        showLoading(true);
        service.getLaunch(getIntent().getIntExtra(ARG_FLIGHT_NUMBER, 0))
                .enqueue(new Callback<Launch>() {
                    @Override
                    public void onResponse(Call<Launch> call, Response<Launch> response) {
                        showLoading(false);
                        showLaunch(response.body());
                    }

                    @Override
                    public void onFailure(Call<Launch> call, Throwable t) {
                        showLoading(false);
                    }
                });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    void showLoading(boolean loading) {
        progressBar.setVisibility(loading ? View.VISIBLE : View.GONE);
        infoLayout.setVisibility(loading ? View.GONE : View.VISIBLE);
    }

    void showLaunch(Launch launch) {
        rocketName.setText(launch.getRocket().getName());
        rocketType.setText(launch.getRocket().getType());
        siteName.setText(launch.getLaunchSite().getSiteName());
        details.setText(launch.getDetails());
    }

}
