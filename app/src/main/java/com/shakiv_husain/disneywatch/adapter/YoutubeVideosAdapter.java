package com.shakiv_husain.disneywatch.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.RecyclerView;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.shakiv_husain.disneywatch.R;
import com.shakiv_husain.disneywatch.databinding.VideosContainerBinding;
import com.shakiv_husain.disneywatch.models.Video.VideoModel;

import java.util.List;

public class YoutubeVideosAdapter extends RecyclerView.Adapter<YoutubeVideosAdapter.YoutubeVideosAdapterViewHolder> {

    private List<VideoModel> videoIds;
    private Lifecycle lifecycle;
    private LayoutInflater layoutInflater;

    public YoutubeVideosAdapter(List<VideoModel> videoIds, Lifecycle lifecycle) {
        this.videoIds = videoIds;
        this.lifecycle = lifecycle;
    }

    @NonNull
    @Override
    public YoutubeVideosAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }

        VideosContainerBinding videosContainerBinding = DataBindingUtil.inflate(layoutInflater, R.layout.videos_container, parent, false);
        lifecycle.addObserver(videosContainerBinding.youtubePlayer);
        return new YoutubeVideosAdapterViewHolder(videosContainerBinding);


//        YouTubePlayerView youTubePlayerView = (YouTubePlayerView) LayoutInflater.from(parent.getContext()).inflate(R.layout.videos_container, parent, false);
//        lifecycle.addObserver(youTubePlayerView);
//        return new YoutubeVideosAdapterViewHolder(youTubePlayerView);

    }

    @Override
    public void onBindViewHolder(@NonNull YoutubeVideosAdapterViewHolder holder, int position) {
        holder.bindVideo(videoIds.get(position).getKey());
    }

    @Override
    public int getItemCount() {
        return videoIds.size();
    }

    class YoutubeVideosAdapterViewHolder extends RecyclerView.ViewHolder {

        private YouTubePlayer youTubePlayer;
        private YouTubePlayerView youTubePV;
        private String currentVideoId;

        public YoutubeVideosAdapterViewHolder(@NonNull VideosContainerBinding videosContainerBinding) {
            super(videosContainerBinding.getRoot());
            youTubePV = videosContainerBinding.youtubePlayer;


            youTubePV.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                @Override
                public void onReady(@NonNull YouTubePlayer youTubePlayer1) {
                    super.onReady(youTubePlayer1);
                    youTubePlayer = youTubePlayer1;
                    youTubePlayer.cueVideo(currentVideoId, 0);
                }
            });

        }


        void bindVideo(String videoId) {
            currentVideoId = videoId;
            if (youTubePlayer == null) {
                return;
            }
            youTubePlayer.cueVideo(videoId, 0);
        }
    }
}
