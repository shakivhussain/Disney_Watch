package com.shakiv_husain.disneywatch.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.shakiv_husain.disneywatch.models.images.ImageResponse;
import com.shakiv_husain.disneywatch.repositories.MovieImagesRepository;

public class MovieImagesViewModel extends ViewModel {

    private MovieImagesRepository movieImagesRepository;

    public MovieImagesViewModel() {
        this.movieImagesRepository = new MovieImagesRepository();
    }


    public LiveData<ImageResponse> getMovieImages(String movie_id) {
        Log.d("getTheResponse", "getMovieImages: "+movieImagesRepository.getMovieImages(movie_id));
        return movieImagesRepository.getMovieImages(movie_id);
    }

}
