package com.shakiv_husain.disneywatch.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.shakiv_husain.disneywatch.models.Video.VideoResponse;
import com.shakiv_husain.disneywatch.repositories.MovieVideosRepository;

public class MovieVideosViewModel extends ViewModel {


    private MovieVideosRepository videosRepository;

    public MovieVideosViewModel() {
        this.videosRepository = new MovieVideosRepository();
    }

    public LiveData<VideoResponse> getVideos(String movie_id) {
        return videosRepository.getVideos(movie_id);
    }


}
