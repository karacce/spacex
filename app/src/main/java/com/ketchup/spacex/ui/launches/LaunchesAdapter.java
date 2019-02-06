package com.ketchup.spacex.ui.launches;

import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.ketchup.spacex.BuildConfig;
import com.ketchup.spacex.R;
import com.ketchup.spacex.data.Launch;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LaunchesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static int VIEW_PROGRESS = -1;

    public interface OnLaunchClickListener {
        void onLaunchClick(Launch launch);
    }

    public interface OnLoadMoreListener {
        void onLoadMore();
    }

    private List<Launch> launches;

    private OnLaunchClickListener clickListener;
    private OnLoadMoreListener loadMoreListener;
    private boolean loading;
    private boolean finish;

    public LaunchesAdapter(RecyclerView recyclerView, List<Launch> launches, final OnLoadMoreListener loadMoreListener) {
        this.launches = launches;
        this.loadMoreListener = loadMoreListener;

        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
            final LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    int visibleItemCount = layoutManager.getChildCount();
                    int totalItemCount = layoutManager.getItemCount();
                    int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
                    if (!loading && !finish && (visibleItemCount + firstVisibleItemPosition) >= totalItemCount &&
                            firstVisibleItemPosition >= 0 &&
                            totalItemCount >= BuildConfig.PAGE_SIZE) {
                        if (loadMoreListener != null) {
                            loadMoreListener.onLoadMore();
                        }
                        loading = true;
                    }
                }
            });
        }
    }

    void setClickListener(OnLaunchClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public void setLoading(boolean loading) {
        this.loading = loading;
    }

    public void setFinish(boolean finish) {
        this.finish = finish;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return i == VIEW_PROGRESS ? new ProgressViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.viewholder_progress, viewGroup, false))
                : new LaunchesViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.viewholder_lauch, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        if (viewHolder instanceof  LaunchesViewHolder) {
            ((LaunchesViewHolder) viewHolder).bind(launches.get(i));
            ((LaunchesViewHolder) viewHolder).itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (clickListener != null) {
                        clickListener.onLaunchClick(launches.get(i));
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return launches.size();
    }

    @Override
    public int getItemViewType(int position) {
        return launches.get(position) != null ? position : VIEW_PROGRESS;
    }

    class LaunchesViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.mission_patch_image_view)
        AppCompatImageView missionPatch;

        @BindView(R.id.rocket_name_text_view)
        AppCompatTextView rocketName;

        @BindView(R.id.mission_name_text_view)
        AppCompatTextView missionName;

        @BindView(R.id.launch_year_text_view)
        AppCompatTextView launchYear;

        LaunchesViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(Launch launch) {
            Glide.with(itemView.getContext()).load(launch.getLinks().getMissionPatch()).into(missionPatch);
            rocketName.setText(launch.getRocket().getName());
            missionName.setText(launch.getMissionName());
            launchYear.setText(launch.getLaunchYear());
        }

    }

    class ProgressViewHolder extends RecyclerView.ViewHolder {

        public ProgressViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }


}
